package com.stu.syllabuskt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * yuan
 * 2020/9/4
 **/
public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBaseUserInfoTable = "create table base_user_info(account varchar(64), password varchar(64))";
        String createUserInfoTable = "create table user_info(id varchar(64), avatar varchar(64), nickname varchar(64), signature varchar(64), semester varchar(64))";
        String createYiBanTable = "create table yiban_table(xnxqName varchar(64), kkbKey varchar(64), kcName varchar(64), jsName varchar(64), ksName varchar(64), sjName varchar(64))";
        db.execSQL(createBaseUserInfoTable);
        db.execSQL(createUserInfoTable);
        db.execSQL(createYiBanTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
