package ru.snakegame.core;

import ru.snakegame.core.math.Vector2;

/**
 * Created by Юрий on 26.05.2016.
 */
public class SnakeParams {
    static int START_LEN = 5;
    static Vector2<Integer> START_FROM = new Vector2<>(5, 5);
    static Vector2<Integer> START_DIRECTION = Directions.MOVE_LEFT;

    static class Directions {
        static Vector2<Integer> MOVE_LEFT = new Vector2<>(-1, 0);
        static Vector2<Integer> MOVE_RIGHT = new Vector2<>(1, 0);
        static Vector2<Integer> MOVE_UP = new Vector2<>(0, -1);
        static Vector2<Integer> MOVE_DOWN = new Vector2<>(0, 1);
    }

    static double START_GAME_SPEED = 0.3;
    static double GAME_SPEED_INCREASE = 2.0; // newGameSpeed = gameSpeed / gameSpeedIncrease
    static double APPLE_TTL = -1.0; // apples time to live (-1 - forever)
    static int MAX_APPLES = 1;
    static double APPLE_TIME_INTERVAL = 1;
}
