package com.stu.syllabuskt;

import android.content.Context;

import com.stu.syllabuskt.db.DBService;
import com.stu.syllabuskt.db.DataBaseHelper;

/**
 * yuan
 * 2020/9/4
 * 管理全局单例类
 **/
public final class StuContext {

    private final static SingleTon<DataBaseHelper, Context> mDataBaseHelper = new SingleTon<DataBaseHelper, Context>() {
        @Override
        protected DataBaseHelper create(Context context) {
            return new DataBaseHelper(context, "syllabus_database", null, 1);
        }
    };

    public static DataBaseHelper getDataBaseHelper(Context mContext) {
        return mDataBaseHelper.get(mContext);
    }

    private final static SingleTon<DBService, Void> mDBService = new SingleTon<DBService, Void>() {
        @Override
        protected DBService create(Void aVoid) {
            return new DBService();
        }
    };

    public static DBService getDBService() {
        return mDBService.get(null);
    }
}
