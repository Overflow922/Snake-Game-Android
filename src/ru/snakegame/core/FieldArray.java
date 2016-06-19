package ru.snakegame.core;

import ru.snakegame.core.math.Vector2;
import ru.snakegame.core.math.VectorCalculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: Юрий
 * Creation: 14.05.2016 at
 * Description:
 */

public class FieldArray implements Iterable<SnakeCell>, Serializable {
    private Vector2<Integer> dims = new Vector2<>(0,0);
    private ArrayList<SnakeCell> field = new ArrayList<>();

    public FieldArray(final Vector2<Integer> size) {
        if (size != null) {
            final int dimX = size.getX();
            final int dimY = size.getY();

            if (dimX >0 && dimY >0) {
                dims = size;

                for (int i=0; i<dimX; i++) {
                    for (int j=0; j<dimY; j++) {
                        SnakeCell cell = new SnakeCell(new Vector2<>(i, j));
                        field.add(cell);
                    }
                }
                CollisionSystem.getInstance().setBorders(new Vector2<>(size.getX()-1, size.getY()-1));
            }
            else {
                throw new IllegalArgumentException("Incorrect dimensions (must be > 0.");
            }
        }
        else {
            throw new NullPointerException("Invalid size of a field array.");
        }
    }

    public void changeCellState(final int pos, CellState cellState) {
        Assertion.getInstance().assertion(pos >= 0 && pos < field.size(), "Incorrect position to change. "+pos);

        SnakeCell n = field.get(pos);
        n.setState(cellState);

        field.set(pos, n);
    }

    public void changeCellState(final Vector2<Integer> pos, CellState cellState) {
        this.changeCellState(this.getPos(pos), cellState);
    }

    public SnakeCell getCell(final Vector2<Integer> pos) {
        return this.field.get(this.getPos(pos));
    }

    private int getPos(final Vector2<Integer> pos) {
        Assertion.getInstance().assertion(pos.getX() >= 0 && pos.getY() >= 0, "Incorrect position to change. "+pos);

        return pos.getX()*dims.getY() + pos.getY();
    }

    public Vector2<Integer> getFieldSize() {
        return dims;
    }

    public Vector2<Integer> getBorders() {
        return dims;
    }

    @Override
    public Iterator<SnakeCell> iterator() {
        Iterator it = new Iterator() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < field.size() && field.get(currentIndex) != null;
            }

            @Override
            public SnakeCell next() {
                return field.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public String toString() {
        String result = "";
        if (field != null) {
            Assertion.getInstance().assertion(field.size() > 0, "Field array is empty");
            Assertion.getInstance().assertion(dims.getX() > 0, "Incorrect dimensions");

            int c = 0;
            for (SnakeCell s: field) {
                result += s.toString() + "\t";
                if (c == dims.getX()-1) {
                    result += System.getProperty("line.separator");
                    c = 0;
                }
                else {
                    c++;
                }
            }
        }
        else {
            result = "Field array is not initialized.";
        }
        return result;
    }
}
