package ru.snakegame.core;

import ru.snakegame.core.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Юрий on 14.05.2016.
 */
public interface Field {

    void setFieldSize(final Vector2<Integer> size);
    Vector2<Integer> getFieldSize();

    FieldArray getFieldArray();
    void setFieldArray(final FieldArray field);

    void changeCellState(final Vector2<Integer> pos, CellState cellState);
}
