package ru.snakegame.core.math;


import java.io.Serializable;
import java.util.Random;

/**
 * Created by Юрий on 14.05.2016.
 */
public class Vector2<T extends Number> implements Serializable {
    private T x = null;
    private T y = null;

    public Vector2() {
    }

    public Vector2(final T x, final T y) {
        this.x = x;
        this.y = y;
    }

    public  Vector2<T> getPos() {
        return new Vector2<>(this.x, this.y);
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public void setPos(final T x, final T y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(final Vector2<T> pos) {
        this.setPos(pos.getX(), pos.getY());
    }

    @Override
    public String toString() {
        return this.getX().toString()+", "+ this.getY().toString(); // +" of type "+ this.getX().getClass().toString();
    }
}
