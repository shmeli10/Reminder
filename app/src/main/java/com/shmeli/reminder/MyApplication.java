package com.shmeli.reminder;

import android.app.Application;

/**
 * Created by Serghei Ostrovschi on 9/21/17.
 */

public class MyApplication extends Application {

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}
