package com.stu.syllabuskt.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * yuan
 * 2020/9/5
 **/
public class ToastUtil {

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
