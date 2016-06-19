package ru.snakegame.core;

import android.util.Log;
import ru.snakegame.core.math.Vector2;
import ru.snakegame.core.math.VectorCalculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Юрий on 26.05.2016.
 */
public class AppleSystem implements Serializable {

    private List<Apple> apples = new ArrayList<>();
    private int appleCount = 0;
    private double delta = 0;
    private double timeToApple = SnakeParams.APPLE_TIME_INTERVAL;

    private final String TAG = AppleSystem.class.getSimpleName();


    public AppleSystem() {
    }

    public void eraseApple(final Apple apple) {
        if (apple != null) {
            this.apples.remove(apple);
            this.appleCount--;
        }
    }

    public void addApple(final FieldArray field) {
        if ( this.appleCount < SnakeParams.MAX_APPLES ) {
            Vector2<Integer> pos;
            do {
                pos = Apple.getRandomPos(field.getBorders());
            } while (field.getCell(pos).getState() != CellState.CELL_EMPTY);

            Apple apple = new Apple();
            apple.setPos(pos);

            apples.add(apple);
            this.appleCount++;
        }
    }

    public void updateDelta(final double delta) {
        this.delta = delta;
        this.timeToApple = this.timeToApple - delta;
    }

    // Проверка пора ли добавлять яблоко
    public boolean check() {
        if (this.timeToApple < 0.0) {
            this.timeToApple = SnakeParams.APPLE_TIME_INTERVAL;
            return true;
        }

        return false;
    }

    public Apple findByPos(final Vector2<Integer> pos) {
        if (apples.size() > 0) {
            for (Apple a : apples) {
                if (VectorCalculation.compare(a.getPos(), pos)) {
                    return a;
                }
            }
        }
        return null;
    }

    public void updatesApples(final FieldArray field) {
        if (apples.size() > 0) {
            Iterator<Apple> iterator = apples.iterator();
            while(iterator.hasNext()) {
                Apple a = iterator.next();
                if (a.checkTimeToLive()) {
                    field.getCell(a.getPos()).setState(CellState.CELL_EMPTY);
                    iterator.remove();
                    this.appleCount--;
                } else {
                    field.changeCellState(a.getPos(), CellState.CELL_APPLE);
                    a.updateTimeToLive(delta);
                }
            }
        }
    }

    public List<Apple> getApples() {
        return apples;
    }

    public void setApples(List<Apple> list) {
        if (list != null) {
            int size = list.size();
            Log.d(TAG, "setApples. list.size = "+size);
            if (size != 0) {
                apples = new ArrayList<>(size);
                Collections.copy(apples, list);
            }
        }

    }

    public void swapApplesCoordinates() {
        if (apples.size() > 0) {
            for (Apple a : apples) {
                    a.swapCoordinates();
            }
        }
    }
}
