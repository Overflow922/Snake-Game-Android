package ru.snakegame.core.math;

/**
 * Created by Юрий on 14.05.2016.
 */
public class VectorCalculation {

    // TODO: Добавить сложение для других типов
    public static final Vector2<Integer> ZERO = new Vector2<>(0,0);

    public static Vector2<Integer> add(Vector2<Integer> a, Vector2<Integer> b) {
        Vector2<Integer> res = new Vector2<>();
        res.setPos(a.getX() + b.getX(), a.getY() + b.getY());
        return res;
    }

    public static Vector2<Integer> sub(Vector2<Integer> a, Vector2<Integer> b) {
        Vector2<Integer> res = new Vector2<>();
        res.setPos(a.getX() - b.getX(), a.getY() - b.getY());
        return res;
    }

    public static boolean compare(final Vector2<Integer> a, final Vector2<Integer> b) {
        return (a.getX().equals(b.getX()) && a.getY().equals(b.getY()));
    }

    public static void swap(Vector2<Integer> a) {
        a.setPos(a.getY(), a.getX());
    }

    public static void rotate(Vector2<Integer> a, double radians) {
        double x = a.getX() * Math.cos(radians) - a.getY() * Math.sin(radians);
        double y = a.getX() * Math.sin(radians) + a.getY()* Math.cos(radians);

        a.setPos((int)Math.round(x), (int)Math.round(y));
    }

    public static void invert(Vector2<Integer> a) {
        a.setPos(-a.getX(), -a.getY());
    }
}
