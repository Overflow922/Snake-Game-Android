package ru.snakegame.settings;

import android.util.Log;
import ru.snakegame.core.Assertion;
import ru.snakegame.core.math.Vector2;
import ru.snakegame.core.math.VectorCalculation;

import java.io.Serializable;

/**
 * Author: Юрий
 * Creation: 02.06.2016 at 18:34
 * Description:
 */
public class GameSettings implements Serializable {
    private static GameSettings ourInstance = new GameSettings();
    private GameStates gameState = GameStates.GAME_MAIN_CYCLE;
    private int orientation = 0;

    private int cells_min_num = 20;
    private Vector2<Integer> dimensions = new Vector2<>(0,0);
    private Vector2<Integer> cellNum = new Vector2<>(0,0);
    private Vector2<Integer> cellDim = new Vector2<>(0,0);
    public static GameSettings getInstance() {
        return ourInstance;
    }

    private static String TAG = GameSettings.class.getSimpleName();

    // TODO: вычесть из размера окна размеры статус бара. Учесть ориентацию устройства.
    private GameSettings() {

    }

    // получение количества клеток по горизонтали и вертикали, для заполнения всей площади экрана
    public void calcCellNum(final Vector2<Integer> dimensions) {
        this.dimensions = dimensions;
        Log.d(TAG, "dims: " + dimensions+", cellNum: "+cellNum);
        // dims: 800, 442, cellNum: 20, 31
        if (cellNum.getY() > 0 || cellNum.getX() > 0)
        {
            if (dimensions.getX() >= dimensions.getY()) {
                if (cellNum.getX() < cellNum.getY()) {
                    cellNum.setPos(cellNum.getY(), cellNum.getX());
                } // cellNum 31, 20
                this.cellDim = new Vector2<>(dimensions.getX() / cellNum.getX(), dimensions.getY() / cellNum.getY());
            } else { // dimensions.getX() < dimensions.getY()
                if (cellNum.getX() > cellNum.getY()) {
                    cellNum.setPos(cellNum.getY(), cellNum.getX());
                }
                this.cellDim = new Vector2<>(dimensions.getX() / cellNum.getX(), dimensions.getY() / cellNum.getY());
            }
        } else {
            if (dimensions.getX() >= dimensions.getY()) {
                int cellSize = dimensions.getY() / this.cells_min_num;
                this.cellDim = new Vector2<>(cellSize, cellSize);
                Assertion.getInstance().assertion(cellSize > 0, "cellSize = 0");
                this.cellNum.setPos(dimensions.getX() / cellSize, dimensions.getY() / cellSize);
            } else {
                int cellSize = dimensions.getX() / this.cells_min_num;
                this.cellDim = new Vector2<>(cellSize, cellSize);
                Assertion.getInstance().assertion(cellSize > 0, "cellSize = 0");
                this.cellNum.setPos(dimensions.getX() / cellSize, dimensions.getY() / cellSize);
            }
        }
    }

    public Vector2<Integer> getCellNum() {
        return cellNum;
    }

    public Vector2<Integer> getDimensions() {
        return dimensions;
    }

    public Vector2<Integer> getCellDim() {
        return cellDim;
    }

    public GameStates getGameState() { return this.gameState; }

    public void setGameState(final GameStates newState) {
        this.gameState = newState;
    }

    public void setOrientation(int rotation) {
        this.orientation = rotation;
    }

    public int getOrientation() {
        return orientation;
    }
    public Vector2<Integer> getMoveVector(int rotation) {
        Vector2<Integer> result = new Vector2<>(0,0);
        switch (rotation) {
            case -1:
                result.setPos(this.cellNum.getX()-1, 0);
                break;
            case 1:
                result.setPos(0, this.cellNum.getY()-1);
                break;
            case 2:
                result.setPos(this.cellNum.getX()-1, this.cellNum.getY()-1);
                break;
            case 3:
                result.setPos(this.cellNum.getX()-1, 0);
                break;
            case 0:
                break;
            default:
                throw new AssertionError("invalid rotation: "+rotation);
        }
        return result;
    }
}
