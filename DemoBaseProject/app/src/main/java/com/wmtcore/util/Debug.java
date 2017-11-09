package com.wmtcore.util;

import android.util.Log;

public class Debug {
    public static final boolean isDebuggable = true;
    public static final String TAG = Debug.class.getSimpleName();

    public static void i(String tag, String message) {
        if (isDebuggable) {
            Log.i(tag, message);
        }
    }

    public static void i(String message) {
        Debug.i(TAG, message);
    }

    public static void e(String tag, String message) {
        if (isDebuggable) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        Debug.e(TAG, message);
    }

    public static void e(String tag, String message, Exception e) {
        if (isDebuggable) {
            Log.e(tag, message);
            e.printStackTrace();
        }
    }


}
