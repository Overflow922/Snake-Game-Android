package ru.snakegame.core;

import android.util.Log;

/**
 * Author: Юрий
 * Creation: 01.06.2016 at 18:38
 * Description:
 */
public class TimerSystem {
    private static TimerSystem ourInstance = new TimerSystem();
    private double start = 0.0;
    private double frameTime = 0.0;
    private double gameSpeed = SnakeParams.START_GAME_SPEED; // seconds

    private boolean isStarted = false;
    private static final String TAG = TimerSystem.class.getSimpleName();

    public static TimerSystem getInstance() {
        return ourInstance;
    }

    private TimerSystem() {
    }

    public void start() {
        isStarted = true;
        this.start = System.nanoTime();
        this.frameTime = System.nanoTime();
 //       Log.d(TAG, "start at"+this.start);
    }

    public void stop() {
        isStarted = false;
    }

    public double getLastFrameTime() {
        if (isStarted) {
            double curTime = System.nanoTime();
            double temp = (curTime - this.frameTime) / 1_000_000_000.0;
            this.frameTime = curTime;
//        Log.d(TAG, "getLastFrameTime: "+temp+", frameTime:"+this.frameTime);
            return temp;
        }
        return 0.0;
    }

    // Проверка пора ли двигать змейку
    public boolean check() {
        if (isStarted) {
            double delta = (System.nanoTime() - this.start) / 1_000_000_000.0;

            //       Log.d(TAG, "check(): "+(delta > this.gameSpeed));
            if (delta > this.gameSpeed) {
                this.start();
                return true;
            }
        }
        return false;
    }

    public void speedUp() {
        if (isStarted) {
            this.gameSpeed = this.gameSpeed / SnakeParams.GAME_SPEED_INCREASE;
        }
    }
}