package ru.snakegame.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import ru.snakegame.core.InputSystem;
import ru.snakegame.core.SnakeDirections;
import ru.snakegame.core.TimerSystem;
import ru.snakegame.settings.GameSettings;
import ru.snakegame.settings.GameStates;

public class MyActivity extends Activity {

    private static final String TAG = MyActivity.class.getSimpleName();

    private static long back_pressed;

    private GameView gw;
    private SharedPreferences  mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        gw = new GameView(this);
        setContentView(gw);
        Log.d(TAG, "View added");
        switch(GameSettings.getInstance().getGameState()) {
            case GAME_CONTINUE_MAIN_CYCLE:
                gw.loadGame(this.loadGame());
                GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_CYCLE);
                break;
            case GAME_START_MAIN_CYCLE:
                GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_CYCLE);
                break;
            default:
                break;
        }

        gw.setOnTouchListener(new OnSwipeTouchListener(MyActivity.this) {
            public void onSwipeTop() {
                Log.d(TAG, "Swipe TOP");
                InputSystem.getInstance().put(SnakeDirections.MOVE_UP);
            }
            public void onSwipeRight() {
                Log.d(TAG, "Swipe RIGHT");
                InputSystem.getInstance().put(SnakeDirections.MOVE_RIGHT);
            }
            public void onSwipeLeft() {
                Log.d(TAG, "Swipe LEFT");
                InputSystem.getInstance().put(SnakeDirections.MOVE_LEFT);
            }
            public void onSwipeBottom() {
                Log.d(TAG, "Swipe BOTTOM");
                InputSystem.getInstance().put(SnakeDirections.MOVE_DOWN);
            }

        });
    }

    @Override
    public  void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }
    @Override
    public void onRestart(){
        Log.i(TAG, "onRestart");
        super.onRestart();
        GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_CYCLE);
        TimerSystem.getInstance().start();
    }

    @Override
    public  void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();

        GameSettings.getInstance().setGameState(GameStates.GAME_PAUSE);
        TimerSystem.getInstance().stop();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        GameSettings.getInstance().setGameState(GameStates.GAME_MAIN_CYCLE);
        TimerSystem.getInstance().start();
        Display display = ((WindowManager) gw.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        Toast.makeText(gw.getContext(), rotation*90+" degree", Toast.LENGTH_SHORT).show();
    }

    private String saveGame() {
        SharedPreferences prefs;
        prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String result = gw.saveGame();
        prefsEditor.putString(WinConsts.SAVE_NAME, result);
        prefsEditor.apply();
        return result;
    }

    private String loadGame() {
        SharedPreferences prefs;
        prefs = getPreferences(MODE_PRIVATE);

        prefs.edit();
        return prefs.getString(WinConsts.SAVE_NAME, "");
    }

    @Override
    public void onBackPressed() {
        this.saveGame();
        this.finish();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
        this.saveGame();
    }
}
