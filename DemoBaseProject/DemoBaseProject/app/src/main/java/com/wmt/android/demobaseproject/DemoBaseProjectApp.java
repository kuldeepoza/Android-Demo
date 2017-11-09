package com.wmt.android.demobaseproject;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mpt.android.storage.DatabaseConfig;
import com.mpt.android.storage.DatabaseUtil;
import com.mpt.android.storage.SharedPreferenceUtil;
import com.wmtcore.util.Debug;

public class DemoBaseProjectApp extends Application {

    private static DemoBaseProjectApp appInstance;
    private static final String TAG = DemoBaseProjectApp.class.getSimpleName();
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "DemoBaseProject.db";

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        SharedPreferenceUtil.init(this);
            DatabaseUtil.init(this, DATABASE_NAME, DATABASE_VERSION, new DatabaseConfig() {
                @Override
                public void onCreate(SQLiteDatabase db) throws Exception {
                    Debug.e(TAG, "Method: onCreate:");
                }

                @Override
                public void onUpgrade(SQLiteDatabase db) throws Exception {
                    Debug.e(TAG, "Method: onUpgrade: ");
                }
            });
    }

    public static DemoBaseProjectApp getAppInstance() {
        return appInstance;
    }

}
