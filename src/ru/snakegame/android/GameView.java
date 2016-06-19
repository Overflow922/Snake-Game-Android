package ru.snakegame.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import ru.snakegame.core.*;
import ru.snakegame.core.math.Vector2;
import ru.snakegame.core.math.VectorCalculation;
import ru.snakegame.settings.GameSettings;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Юрий
 * Creation: 01.06.2016 at 18:11
 * Description:
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint paint, mBitmapPaint;
    private Snake snake = new Snake();
    private FieldArray map;
    private AppleSystem apples = new AppleSystem();

    private Map<CellState, Bitmap> images = new HashMap<>();

    private static final String TAG = GameView.class.getSimpleName();

    private WindowManager wm;


    public String saveGame() {
        Gson gson = new Gson();

        Type typeOfSrc = new TypeToken<ArrayList<String>>(){}.getType();

        ArrayList<String> result = new ArrayList<>();

        result.add(gson.toJson(snake));
        result.add(gson.toJson(apples));

        return gson.toJson(result, typeOfSrc);
    }

    public void loadGame(String data) {
        if (data.equals(""))
            return;

        Gson gson = new Gson();

        ArrayList<String> list = new ArrayList<String>();
        Type typeOfT = new TypeToken<ArrayList<String>>(){}.getType();

        list = gson.fromJson(data, typeOfT);

        try {
            if (list != null) {
                if (list.get(0) != null) {
                    Snake s = gson.fromJson(list.get(0), Snake.class);
                    if (s != null)
                        snake = s;
                }
                if (list.get(1) != null) {
                    AppleSystem a = gson.fromJson(list.get(1), AppleSystem.class);
                    if (a != null)
                        apples = a;
                }
            }
        } catch (JsonSyntaxException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public GameView(Context context) {
        super(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);
        point.y = point.y - getStatusBarHeight();
        Assertion.getInstance().assertion((point.x > 0 || point.y > 0), "Wrong canvas size");
        mBitmap = Bitmap.createBitmap((int) point.x, (int) point.y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        GameSettings.getInstance().calcCellNum(new Vector2<Integer>(point.x, point.y));
        Log.d(TAG, "cellNum: " + GameSettings.getInstance().getCellNum());

        this.loadImages();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        map = new FieldArray(GameSettings.getInstance().getCellNum());

        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
 //       thread = new MainThread(getHolder(), this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    public void update(final float delta) {
        snake.updateMoveDirection();
        apples.updateDelta(TimerSystem.getInstance().getLastFrameTime());
        if (apples.check()) {
            apples.addApple(map);
        }
        if (TimerSystem.getInstance().check()) {
            snake.move();
            CollisionResult result = CollisionSystem.getInstance().isCollision(map, snake.getHead());

            switch (result) {
                case SNAKE_COLLISION:

                    break;
                case APPLE_COLLISION:
                    Apple apl = apples.findByPos(snake.getHead());
                    Assertion.getInstance().assertion(apl != null, "apl is null");
                    apples.eraseApple(apl);

                    snake.grow();
                    apples.updatesApples(map);
                    break;
                case BORDER_COLLISION:

            }
        }

        snake.push(map);
        apples.updatesApples(map);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int orientation = display.getRotation();
        Log.d(TAG, "last orient: "+ GameSettings.getInstance().getOrientation()+", orient: "+orientation);

        if (orientation - GameSettings.getInstance().getOrientation() != 0) {
            Vector2<Integer> move = GameSettings.getInstance().getMoveVector(orientation-GameSettings.getInstance().getOrientation());
            Log.d(TAG, "move:"+move);
            if (orientation !=0) {
                move = VectorCalculation.add(move, GameSettings.getInstance().getMoveVector(GameSettings.getInstance().getOrientation()));
            }
            Log.d(TAG, "move2:"+move+", last-now: "+(orientation-GameSettings.getInstance().getOrientation()));
            snake.convert(orientation-GameSettings.getInstance().getOrientation(), move);
            GameSettings.getInstance().setOrientation(orientation);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        thread.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

    public void render(Canvas canvas) {
        this.drawClear(canvas);
        this.drawField(map, canvas);
        this.drawGrid(canvas);
    }

    private void drawClear(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    private void drawGrid(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        for (int x=0; x <= GameSettings.getInstance().getCellNum().getX(); x++) {
            if (x % 5 == 0 )
                paint.setColor(Color.RED);
            else
                paint.setColor(Color.WHITE);
            int size = GameSettings.getInstance().getCellDim().getX();
            final int pos = size * x;
            final int sizeY = GameSettings.getInstance().getCellNum().getY() *
                             GameSettings.getInstance().getCellDim().getY();
            canvas.drawLine(pos, 0, pos, sizeY, paint);
        }
        for (int y=0; y <= GameSettings.getInstance().getCellNum().getY(); y++) {
            if (y % 5 == 0 )
                paint.setColor(Color.RED);
            else
                paint.setColor(Color.WHITE);
            int size = GameSettings.getInstance().getCellDim().getY();
            final int pos = size * y;
            final int sizeX = GameSettings.getInstance().getCellNum().getX() *
                    GameSettings.getInstance().getCellDim().getX();
            canvas.drawLine(0, pos, sizeX, pos, paint);
        }
    }

    private void drawField(FieldArray map, Canvas canvas) {
        for (SnakeCell f : map) {
            Bitmap img = images.get(f.getState());
            Vector2<Integer> pos = f.getPos();
            canvas.drawBitmap(img, pos.getX() * GameSettings.getInstance().getCellDim().getX(),
                                   pos.getY() * GameSettings.getInstance().getCellDim().getY(), paint);
        }
    }

    private Bitmap loadImage(String imageId, Vector2<Integer> dims) {
        int ID = getResources().getIdentifier(imageId, "drawable", getContext().getPackageName());
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), ID),
                                                 dims.getX(), dims.getY(), true);
    }

    private void loadImages() {
        Vector2<Integer> cellDim = GameSettings.getInstance().getCellDim();

        images.put(CellState.CELL_APPLE, loadImage("snake_apple", cellDim));
//        int snakeID = getResources().getIdentifier("snake_snake" , "drawable", getContext().getPackageName());
//        Bitmap snake =  Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), snakeID), cellDim.getX(), cellDim.getY(), true);
        Bitmap snake = loadImage("snake_snake", cellDim);
        images.put(CellState.CELL_SNAKE, snake);
        images.put(CellState.CELL_SNAKE_HEAD, snake);
        images.put(CellState.CELL_SNAKE_TAIL, snake);
//        int emptyID = getResources().getIdentifier("snake_empty" , "drawable", getContext().getPackageName());
//        Bitmap empty = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), emptyID), cellDim.getX(), cellDim.getY(), true);
        images.put(CellState.CELL_EMPTY, loadImage("snake_empty", cellDim));
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
