package ru.snakegame.core;

import android.util.Log;
import ru.snakegame.core.math.Vector2;
import ru.snakegame.core.math.VectorCalculation;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Author: Юрий
 * Creation: 01.06.2016 at 18:38
 * Description:
 */
public class Snake implements Serializable {

    private int len = SnakeParams.START_LEN;
    private LinkedList<Vector2<Integer>> cells;
    private boolean isGrow = false;
    private Vector2<Integer> moveDirection = SnakeParams.START_DIRECTION;
    private Vector2<Integer> changeDirection = SnakeParams.START_DIRECTION;

    private static final String TAG = Snake.class.getSimpleName();

    public Snake() {
        cells = new LinkedList<>();
        createSnake();
    }

    public void grow() {
        //TODO: сделать задержку увеличения хвоста 1 ход
        len++;
        Assertion.getInstance().assertion(cells.size() >2, "too small len");
        this.isGrow = true;
    }

    private void createSnake() {
        Vector2<Integer> curPos = VectorCalculation.add(SnakeParams.START_FROM, SnakeParams.START_DIRECTION);

        for (int i=0; i < this.len; i++) {
            curPos = VectorCalculation.sub(curPos, SnakeParams.START_DIRECTION);
            Vector2<Integer> res = curPos;
            cells.add(res);
        }
    }

    public void push(FieldArray field) {
        // TODO: rewrite!
        for (SnakeCell cell: field) {
            if (cell.getState() == CellState.CELL_SNAKE) {
                cell.setState(CellState.CELL_EMPTY);
            }
        }
        for (Vector2<Integer> i: cells) {
            field.changeCellState(i, CellState.CELL_SNAKE);
        }
    }

    public void setMoveDirection(final Vector2<Integer> new_dir) {
        this.moveDirection = new_dir;
    }

    public Vector2<Integer> getMoveDirection() {
        return this.moveDirection;
    }

    public void updateMoveDirection() {
 //       Log.d(TAG, "updating move direction");

        Vector2<Integer> newDir = new Vector2<>(0, 0);
//        Log.d(TAG, "Current dir is " + newDir);

        while (InputSystem.getInstance().isNext()) {
            SnakeDirections result = InputSystem.getInstance().getNext();

            switch (result) {
                case MOVE_DOWN:
                    newDir = SnakeParams.Directions.MOVE_DOWN;
                    break;
                case MOVE_LEFT:
                    newDir = SnakeParams.Directions.MOVE_LEFT;
                    break;
                case MOVE_RIGHT:
                    newDir = SnakeParams.Directions.MOVE_RIGHT;
                    break;
                case MOVE_UP:
                    newDir = SnakeParams.Directions.MOVE_UP;
                    break;
            }
        }

        if (!VectorCalculation.compare(newDir, VectorCalculation.ZERO )) {
            Vector2<Integer> t = VectorCalculation.add(newDir, this.moveDirection);
            if (!VectorCalculation.compare(t, VectorCalculation.ZERO )) {
                this.changeDirection = newDir;
                Log.d(TAG, "Change direction is " + newDir);
            }
        }
//        Log.d(TAG, "END of setMoveDirection");

    }

    private void changeMoveDirection() {
 //       Log.d(TAG, "Changing Move Direction.");
        Vector2<Integer> t = VectorCalculation.add(this.changeDirection, this.moveDirection);
 //       Log.d(TAG, t.toString());
        if (!VectorCalculation.compare(t, VectorCalculation.ZERO )) {
 //           Log.d(TAG, "Changing move direction to "+ this.changeDirection);
            this.moveDirection = this.changeDirection;
        }
    }

    public void move() {
        this.changeMoveDirection();
        if (this.isGrow) {
            cells.addFirst(this.getNextHead());
            this.isGrow = false;
        }
        else {
            Vector2<Integer> newHead = this.getNextHead();
            if (CollisionSystem.getInstance().isBorderCollision(newHead) != CollisionResult.BORDER_COLLISION &&
                    CollisionSystem.getInstance().isSnakeCollision(this) != CollisionResult.SNAKE_COLLISION) {
                cells.addFirst(newHead);
                cells.removeLast();
            }
        }
    }

    public Vector2<Integer> getHead() {
        return cells.getFirst();
    }

    public Vector2<Integer> getNextHead() { return VectorCalculation.add(this.getHead(), this.moveDirection);}

    public boolean isBelong(final Vector2<Integer> coords) {
        for (Vector2<Integer> i: cells) {
            if (VectorCalculation.compare(coords, i))
                return true;
        }
        return false;
    }

    public void convert(int rotation, Vector2<Integer> move) {
        if (rotation != 0) {
            double radians = Math.toRadians(rotation * (-90));
            for (int i = 0; i< cells.size(); i++) {
                
                Log.d(TAG, "convert. cell: "+cells.get(i));
                VectorCalculation.rotate(cells.get(i), radians);
                Log.d(TAG, "convert. rotate: "+cells.get(i));
                cells.set(i, VectorCalculation.add(cells.get(i), move));
                Log.d(TAG, "convert. move: "+cells.get(i));
            }
 //         VectorCalculation.rotate(SnakeParams.Directions.MOVE_DOWN, radians);
 //         VectorCalculation.rotate(SnakeParams.Directions.MOVE_LEFT, radians);
 //         VectorCalculation.rotate(SnakeParams.Directions.MOVE_RIGHT, radians);
 //         VectorCalculation.rotate(SnakeParams.Directions.MOVE_UP, radians);
        }
        Log.d(TAG, "convert. Snake head: "+this.getHead());
    }

    @Override
    public String toString() {
        return "Snake len("+len+"), direction("+moveDirection.toString()+").";
    }
}
