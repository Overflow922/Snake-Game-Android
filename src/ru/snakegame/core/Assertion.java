package ru.snakegame.core;

import ru.snakegame.android.BuildConfig;

/**
 * Author: Юрий
 * Creation: 02.06.2016 at 16:06
 * Description:
 */
public class Assertion {
    private static Assertion ourInstance = new Assertion();
    private static AssertionStates state = AssertionStates.ANDROID_DEBUG;

    public static Assertion getInstance() {
        return ourInstance;
    }

    private Assertion() {
    }

    public void assertion(final boolean logic, final String error) {
        switch(state) {
            case ANDROID_DEBUG:
                if (BuildConfig.DEBUG && !logic) {
                    throw new AssertionError(error);
                }
                break;
            case JAVA_DEBUG:
                assert (logic) : error;
                break;
        }
    }
}
