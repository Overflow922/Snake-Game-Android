package ru.snakegame.core;


import ru.snakegame.core.math.Vector2;

/**
 * Created by Юрий on 14.05.2016.
 * Интефейс ячейки игрового поля
 */
public interface Cell {
    void setPos(final int x, final int y);
    Vector2<Integer> getPos();
    void setPos(Vector2<Integer> newPos);

    CellState getState();
    void setState(final CellState newState);
}
