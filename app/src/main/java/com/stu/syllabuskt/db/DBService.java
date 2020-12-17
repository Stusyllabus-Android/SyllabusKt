package com.stu.syllabuskt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.stu.syllabuskt.StuContext;
import com.stu.syllabuskt.bean.YiBanTimeTable;

import java.util.ArrayList;
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

    public List<YiBanTimeTable.TableBean> getSyllabus(Context context) {
        List<YiBanTimeTable.TableBean> tableBeanList = new LinkedList<>();
        String account = null;
        String xnxq_name = null;
        String kkb_key = null;
        String kc_name = null;
        String js_name = null;
        String ks_name = null;
        String sj_name = null;
        String sqlForOfficialSyllabus = "select * from official_syllabus";
        Cursor cursorForOfficialSyllabus = StuContext.getDataBaseHelper(context).getReadableDatabase().rawQuery(sqlForOfficialSyllabus, null);
        while (cursorForOfficialSyllabus.moveToNext()) {
            account = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("account"));
            xnxq_name = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("xnxqName"));
            kkb_key = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("kkbKey"));
            kc_name = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("kcName"));
            js_name = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("jsName"));
            ks_name = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("ksName"));
            sj_name = cursorForOfficialSyllabus.getString(cursorForOfficialSyllabus.getColumnIndex("sjName"));
            if (xnxq_name != null && account.equals(StuContext.getDBService().getUserAccount(context))) tableBeanList.add(new YiBanTimeTable.TableBean(xnxq_name, Integer.parseInt(kkb_key), kc_name, js_name, ks_name, sj_name));
            else break;
        }
        cursorForOfficialSyllabus.close();
        String sqlForCustomizedSyllabus = "select * from customized_syllabus";
        Cursor cursorForCustomizedSyllabus = StuContext.getDataBaseHelper(context).getReadableDatabase().rawQuery(sqlForCustomizedSyllabus, null);
        while (cursorForCustomizedSyllabus.moveToNext()) {
            account = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("account"));
            xnxq_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("xnxqName"));
            kkb_key = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("kkbKey"));
            kc_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("kcName"));
            js_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("jsName"));
            ks_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("ksName"));
            sj_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("sjName"));
            if (xnxq_name != null && account.equals(StuContext.getDBService().getUserAccount(context))) tableBeanList.add(new YiBanTimeTable.TableBean(xnxq_name, Integer.parseInt(kkb_key), kc_name, js_name, ks_name, sj_name));
            else continue;
        }
        cursorForCustomizedSyllabus.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        return tableBeanList;
    }

    public ArrayList<YiBanTimeTable.TableBean> getCustomizedSyllabus(Context context, String account) {
        ArrayList<YiBanTimeTable.TableBean> tableBeanList = new ArrayList<>();
        String xnxq_name = null;
        String kkb_key = null;
        String kc_name = null;
        String js_name = null;
        String ks_name = null;
        String sj_name = null;
        String sqlForCustomizedSyllabus = "select * from customized_syllabus where account like ? ";
        Cursor cursorForCustomizedSyllabus = StuContext.getDataBaseHelper(context).getReadableDatabase().rawQuery(sqlForCustomizedSyllabus, new String[]{account});
        while (cursorForCustomizedSyllabus.moveToNext()) {
            xnxq_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("xnxqName"));
            kkb_key = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("kkbKey"));
            kc_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("kcName"));
            js_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("jsName"));
            ks_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("ksName"));
            sj_name = cursorForCustomizedSyllabus.getString(cursorForCustomizedSyllabus.getColumnIndex("sjName"));
            if (xnxq_name != null && account.equals(StuContext.getDBService().getUserAccount(context))) tableBeanList.add(new YiBanTimeTable.TableBean(xnxq_name, Integer.parseInt(kkb_key), kc_name, js_name, ks_name, sj_name));
            else break;
        }
        cursorForCustomizedSyllabus.close();
        StuContext.getDataBaseHelper(context).getReadableDatabase().close();
        return tableBeanList;
    }

    public void deleteInCustomizedSyllabus(Context context, Long id) {
        String sql = "delete from customized_syllabus where kkbKey = '" + id + "' ";
        StuContext.getDataBaseHelper(context).getWritableDatabase().execSQL(sql);
        StuContext.getDataBaseHelper(context).getWritableDatabase().close();
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

    public void writeSyllabus(Context context, String account, YiBanTimeTable.TableBean tableBean, SyllabusSourceType syllabusSourceType) {
        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("xnxqName", tableBean.xnxqName);
        values.put("kkbKey", tableBean.kkbKey);
        values.put("kcName", tableBean.kcName);
        values.put("jsName", tableBean.jsName);
        values.put("ksName", tableBean.ksName);
        values.put("sjName", tableBean.sjName);
        if (syllabusSourceType == SyllabusSourceType.Official) {
            StuContext.getDataBaseHelper(context)
                    .getWritableDatabase()
                    .insert("official_syllabus", null, values);
        } else if (syllabusSourceType == SyllabusSourceType.Customized) {
            StuContext.getDataBaseHelper(context)
                    .getWritableDatabase()
                    .insert("customized_syllabus", null, values);
        }
        StuContext.getDataBaseHelper(context).getWritableDatabase().close();
    }

    public void clearOfficialSyllabusData(Context context) {
        StuContext.getDataBaseHelper(context).getWritableDatabase().delete("official_syllabus", null, new String[]{});
    }

    public void clearAllData(Context context) {
        StuContext.getDataBaseHelper(context).getWritableDatabase().delete("base_user_info", null, new String[]{});
        StuContext.getDataBaseHelper(context).getWritableDatabase().delete("official_syllabus", null, new String[]{});
        StuContext.getDataBaseHelper(context).close();
    }
}
