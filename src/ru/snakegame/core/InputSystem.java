package ru.snakegame.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юрий on 26.05.2016.
 */
public class InputSystem {
    private static InputSystem ourInstance = new InputSystem();

    private List<SnakeDirections> input = new ArrayList<>();

    private static final String TAG = TimerSystem.class.getSimpleName();

    public static InputSystem getInstance() {
        return ourInstance;
    }

    private InputSystem() {
    }

    public void put(SnakeDirections direction) {
        if ( !input.contains(direction) )
            Log.d(TAG, "pushing " + direction + " to Input list");
            input.add( direction );
    }

    public void remove(SnakeDirections direction) {
        input.remove( direction );
    }

    public boolean contains(final SnakeDirections direction) {
        if (this.input.contains(direction)) {
            Log.d(TAG, "pulling " + direction + " from Input list");
            input.remove(direction);
            return true;
        }
        return false;
    }

    public boolean isNext() {
        return (input.size() > 0);
    }

    public SnakeDirections getNext() {
        if (this.isNext())
        {
            SnakeDirections result =input.get(0);
            input.remove(0);
            return result;
        }
        return null;
    }
}
