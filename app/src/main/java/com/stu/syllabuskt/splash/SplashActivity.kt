package com.stu.syllabuskt.splash

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.toLoginView
import com.stu.syllabuskt.toMainView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealSplashBusiness()
    }

    private fun dealSplashBusiness() {
        var account: String? = null
        var password: String? = null
        runBlocking(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                delay(1000)
            }
            withContext(Dispatchers.IO) {
                val sqLiteDatabase = StuContext.getDataBaseHelper(this@SplashActivity).readableDatabase
                val sql2QueryBaseUserInfo = "select * from base_user_info"
                val cursor = sqLiteDatabase.rawQuery(sql2QueryBaseUserInfo, null)
                while (cursor.moveToNext()) {
                    account = cursor.getString(cursor.getColumnIndex("account"))
                    password = cursor.getString(cursor.getColumnIndex("password"))
                }
                cursor.close()
                sqLiteDatabase.close()
            }
            if (account == null || password == null) toLoginView()
            else toMainView()
        }
    }
}
