package com.stu.syllabuskt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.stu.syllabuskt.StuContext;

/**
 * yuan
 * 2020/9/5
 * 管理本地数据库SQLite的读写
 **/
public class DBService {

    public String getUserAccount(Context context) {
        String account = null;
        Cursor cursor = StuContext.getDataBaseHelper(context).getReadableDatabase()
                .rawQuery("select * from base_user_info", null);
        while(cursor.moveToNext()) {
            account = cursor.getString(cursor.getColumnIndex("account"));
        }
        cursor.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        return account == null ? "" : account;
    }

    public String getUserPassword(Context context) {
        String password = null;
        Cursor cursor = StuContext.getDataBaseHelper(context).getReadableDatabase()
                .rawQuery("select * from base_user_info", null);
        while(cursor.moveToNext()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
        }
        cursor.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        return password == null ? "" : password;
    }

    public void writeUserAccount(Context context, String account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account);
        StuContext.getDataBaseHelper(context)
                .getWritableDatabase()
                .insert("base_user_info", null, contentValues);
        StuContext.getDataBaseHelper(context).getWritableDatabase().close();
    }

    public void writeUserPassword(Context context, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        StuContext.getDataBaseHelper(context)
                .getWritableDatabase()
                .insert("base_user_info", null, contentValues);
        StuContext.getDataBaseHelper(context).getWritableDatabase().close();
    }
}
