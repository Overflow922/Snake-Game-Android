package ru.snakegame.ui;

/**
 * Created by Юрий on 14.05.2016.
 */
/*
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
*/
import ru.snakegame.core.*;
import ru.snakegame.core.math.Vector2;
import ru.snakegame.core.math.VectorCalculation;

import java.util.HashMap;
import java.util.Map;

public class Window /* extends Application*/ {
/*
    private int gridSize = WinConsts.BASIC_GRID_SIZE;
    private int cellNums = WinConsts.BASIC_CELL_NUMS;
    private Snake snake = new Snake();

    private GraphicsContext gc;

    private Map<CellState, Image> images = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws java.io.IOException {

        loadImages();
        Canvas canvas;

        canvas = new Canvas(WinConsts.WINDOW_SIZE, WinConsts.WINDOW_SIZE);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);
        grid.getChildren().add(root);

        primaryStage.setTitle(WinConsts.NAME);
        Scene theScene = new Scene( grid );
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);

        FieldArray map = new FieldArray(new Vector2<>(this.cellNums, this.cellNums));

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        InputSystem.getInstance().put(e);
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        InputSystem.getInstance().remove(e);
                    }
                });

        final long startNanoTime = System.nanoTime();
        TimerSystem.getInstance().start();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1_000_000_000.0;

                snake.updateMoveDirection();
                AppleSystem.getInstance().updateDelta(TimerSystem.getInstance().getLastFrameTime());
                if (AppleSystem.getInstance().check()) {
                    AppleSystem.getInstance().addApple(map);
                }
                if (TimerSystem.getInstance().check())
                {
                    snake.move();
                    CollisionResult result =  CollisionSystem.getInstance().isCollision(map,snake.getHead());

                    switch (result) {
                        case SNAKE_COLLISION:
                            this.stop();
                            break;
                        case APPLE_COLLISION:
                            Apple apl = AppleSystem.getInstance().
                                    findByPos(snake.getHead());
                            assert (apl != null) : "apl is null";
                            AppleSystem.getInstance().eraseApple(apl);

                            snake.grow();
                            AppleSystem.getInstance().updatesApples(map);
                            break;
                        case BORDER_COLLISION:
                            this.stop();
                    }

                }

                snake.push(map);
                AppleSystem.getInstance().updatesApples(map);
                // background image clears canvas
                drawClear();
                drawField(map);
                drawGrid();
            }
        }.start();

        primaryStage.show();
    }

    public void drawClear() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, WinConsts.WINDOW_SIZE, WinConsts.WINDOW_SIZE);
    }

    public  void drawField(FieldArray map) {
        for (SnakeCell f : map) {
            Image img = images.get(f.getState());
            Vector2<Integer> pos = f.getPos();
            gc.drawImage(img, pos.getX() * gridSize, pos.getY() * gridSize);
        }
    }


    public void drawGrid() {
        final int winSize = this.gridSize * this.cellNums;
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);
        for (int i=0; i <= cellNums; i++) {
            final int pos = this.gridSize * i;
            this.gc.strokeLine(pos, 0, pos, winSize);
            this.gc.strokeLine(0, pos, winSize, pos);
        }
    }

    public void setGridSize(final int size) {
        assert (size > WinConsts.MIN_CELL_SIZE) : "Wrong value of size param" + size;

        gridSize = size;
        cellNums = WinConsts.WINDOW_SIZE / size;
    }

    private void setResInPos(final Image img, final int x, final int y) {
        assert (x >= 0 && y >=0) : "Wrong x ("+x+") or y("+y+") params";
    //    Image img = new Image(name, (double)this.gridSize, (double)this.gridSize, false, false);
        double posX = x * this.gridSize;
        double posY = y * this.gridSize;

        this.gc.drawImage(img, posX, posY);
    }

    public void loadImages() {
        images.put(CellState.CELL_APPLE, new Image("snake-apple.png"));
        images.put(CellState.CELL_SNAKE, new Image("snake-snake.png"));
        images.put(CellState.CELL_SNAKE_HEAD, new Image("snake-snake.png"));
        images.put(CellState.CELL_SNAKE_TAIL, new Image("snake-snake.png"));
    }
    @Override
    public String toString() {
        return "Game window. Grid Size: " + gridSize + ", cellNums: " + cellNums;
    }
*/
}
