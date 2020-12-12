package com.stu.syllabuskt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.stu.syllabuskt.StuContext;
import com.stu.syllabuskt.bean.YiBanTimeTable;

import java.util.LinkedList;
import java.util.List;

/**
 * yuan
 * 2020/9/5
 * 管理本地数据库SQLite的读写
 **/
public class DBService {

    public String getUserAccount(Context context) {
        String account = null;
        Cursor cursor = StuContext.getDataBaseHelper(context).getReadableDatabase()
                .rawQuery("select account from base_user_info", null);
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
                .rawQuery("select password from base_user_info", null);
        while(cursor.moveToNext()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
        }
        cursor.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        return password == null ? "" : password;
    }

    public String getSemester(Context context) {
        String semester = null;
        String sql = "select * from user_info";
        Cursor cursor = StuContext.getDataBaseHelper(context).getReadableDatabase()
                .rawQuery(sql, null);
        while (cursor.moveToNext()) {
            semester = cursor.getString(cursor.getColumnIndex("semester"));
        }
        cursor.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        if (semester == null) {
            semester = "Non-existent";
        }
        return semester;
    }

    public List<YiBanTimeTable.TableBean> getTimeTable(Context context) {
        List<YiBanTimeTable.TableBean> tableBeanList = new LinkedList<>();
        String xnxq_name = null;
        String kkb_key = null;
        String kc_name = null;
        String js_name = null;
        String ks_name = null;
        String sj_name = null;
        String sql = "select * from yiban_table";
        Cursor cursor = StuContext.getDataBaseHelper(context).getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            xnxq_name = cursor.getString(cursor.getColumnIndex("xnxqName"));
            kkb_key = cursor.getString(cursor.getColumnIndex("kkbKey"));
            kc_name = cursor.getString(cursor.getColumnIndex("kcName"));
            js_name = cursor.getString(cursor.getColumnIndex("jsName"));
            ks_name = cursor.getString(cursor.getColumnIndex("ksName"));
            sj_name = cursor.getString(cursor.getColumnIndex("sjName"));
            if (xnxq_name != null) tableBeanList.add(new YiBanTimeTable.TableBean(xnxq_name, Integer.parseInt(kkb_key), kc_name, js_name, ks_name, sj_name));
            else break;
        }
        cursor.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        return tableBeanList;
    }

    public void writeBaseUserInfo(Context context, String account, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account);
        contentValues.put("password", password);
        StuContext.getDataBaseHelper(context)
                .getWritableDatabase()
                .insert("base_user_info", null, contentValues);
        StuContext.getDataBaseHelper(context).getWritableDatabase().close();
    }

    public void writeSemester(Context context, String semester) {
        ContentValues values = new ContentValues();
        values.put("semester", semester);
        StuContext.getDataBaseHelper(context).getWritableDatabase().insert("user_info", null, values);
        StuContext.getDataBaseHelper(context).close();
    }

    public void updateSemester(Context context, String semester) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("semester", semester);
        StuContext.getDataBaseHelper(context).getWritableDatabase().update("user_info", contentValues, null, null);
        StuContext.getDataBaseHelper(context).close();
    }

    public void writeTimeTable(Context context, YiBanTimeTable.TableBean tableBean) {
        ContentValues values = new ContentValues();
        values.put("xnxqName", tableBean.xnxqName);
        values.put("kkbKey", tableBean.kkbKey);
        values.put("kcName", tableBean.kcName);
        values.put("jsName", tableBean.jsName);
        values.put("ksName", tableBean.ksName);
        values.put("sjName", tableBean.sjName);
        StuContext.getDataBaseHelper(context)
                .getWritableDatabase()
                .insert("yiban_table", null, values);
        StuContext.getDataBaseHelper(context).getWritableDatabase().close();
    }

    public void clearAllData(Context context) {
        StuContext.getDataBaseHelper(context).getWritableDatabase().delete("base_user_info", null, new String[]{});
        StuContext.getDataBaseHelper(context).getWritableDatabase().delete("user_info", null, new String[]{});
        StuContext.getDataBaseHelper(context).getWritableDatabase().delete("yiban_table", null, new String[]{});
        StuContext.getDataBaseHelper(context).close();
    }
}
