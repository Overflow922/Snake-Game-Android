package ru.snakegame.android;

import android.graphics.Canvas;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;
import ru.snakegame.core.TimerSystem;
import ru.snakegame.settings.GameSettings;
import ru.snakegame.settings.GameStates;

/**
 * Author: Юрий
 * Creation: 01.06.2016 at 18:38
 * Description:
 */
public class MainThread extends Thread {

    // desired fps
    private final static int 	MAX_FPS = 50;
    // maximum number of frames to be skipped
    private final static int	MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    private static final String TAG = MainThread.class.getSimpleName();

    private Object mPauseLock;
    private boolean mPaused;
    private boolean mFinished;

    // flag to hold game state
    private boolean running;

    private SurfaceHolder surfaceHolder;
    private GameView gamePanel;

    public void setRunning(boolean running) {
        this.running = running;
    }
    public MainThread(SurfaceHolder surfaceHolder, GameView gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;
    }

    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Starting game loop");

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped

        sleepTime = 0;

        TimerSystem.getInstance().start();
        while (!mFinished) {
            if (GameSettings.getInstance().getGameState() == GameStates.GAME_MAIN_CYCLE) {
                canvas = null;
                // try locking the canvas for exclusive pixel editing
                // in the surface
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        beginTime = System.currentTimeMillis();
                        framesSkipped = 0;    // resetting the frames skipped
                        // render state to the screen
                        // draws the canvas on the panel
                        this.gamePanel.render(canvas);
                        // calculate how long did the cycle take
                        timeDiff = System.currentTimeMillis() - beginTime;
                        // update game state
                        this.gamePanel.update(timeDiff);
                        // calculate sleep time
  /*                  sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            Log.d(TAG, e.getMessage());
                        }
                    }

                    float sleep_delta = System.currentTimeMillis();
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    sleep_delta = System.currentTimeMillis() - sleep_delta;
                    this.gamePanel.update(sleep_delta);
                    */
                    }
                } finally {
                    // in case of an exception the surface is not left in
                    // an inconsistent state
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }    // end finally
            }
            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        }
    }

    public void onPause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    /**
     * Call this on resume.
     */
    public void onResume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }
}
