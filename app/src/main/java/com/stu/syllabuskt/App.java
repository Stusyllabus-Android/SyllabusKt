package com.stu.syllabuskt;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * Create by yuan on 2020/12/13
 */
public class App extends Application {

    private String TAG = "SyllabusKt";

    public static int versionCode = Integer.MAX_VALUE;
    public static String versionName = "";

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Application onCreate");
        context = getApplicationContext();
        initVersionInfo();
        UMConfigure.preInit(this, "5fda1746dd2891533921a2f2", "Umeng");
        UMConfigure.init(this, "5fda1746dd2891533921a2f2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.setLogEnabled(true);
    }

    public static Context getContext() {
        return context;
    }

    private void initVersionInfo() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            versionName= packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
