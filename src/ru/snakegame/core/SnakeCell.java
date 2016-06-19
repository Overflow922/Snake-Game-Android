package ru.snakegame.core;

import ru.snakegame.core.math.Vector2;

/**
 * Created by Юрий on 14.05.2016.
 */
public class SnakeCell implements Cell {
    private CellState currentState = CellState.CELL_EMPTY;
    private Vector2<Integer> pos = new Vector2<>(0,0);

    public SnakeCell() {}

    public SnakeCell(final Vector2<Integer> pos, final CellState state) {
        this.currentState = state;
        this.pos = pos;
    }

    public SnakeCell(final Vector2<Integer> pos) {
        this.pos = pos;
    }

    public void setPos(final int x, final int y)    {
        pos.setPos(x, y);
    }

    public Vector2<Integer> getPos()    {
        return pos.getPos();
    }

    public void setPos(Vector2<Integer> newPos) {
        pos.setPos(newPos);
    }

    public CellState getState()    {
        return currentState;
    }

    public void setState(final CellState newState) {
        currentState = newState;
    }

    @Override
    public String toString() {
        return currentState.toString() + "; (" + pos.toString() + ");";
    }
}
