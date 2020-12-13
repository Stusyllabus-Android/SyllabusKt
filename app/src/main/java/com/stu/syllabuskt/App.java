package com.stu.syllabuskt;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Create by yuan on 2020/12/13
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
