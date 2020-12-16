package com.stu.syllabuskt.login

import android.content.Context
import android.util.Log
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.api.YiBanApi
import com.stu.syllabuskt.bean.YiBanTimeTable
import com.stu.syllabuskt.bean.YiBanToken
import com.stu.syllabuskt.db.SyllabusSourceType
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.net.URLEncoder

/**
 *yuan
 *2020/9/4
 **/
class LoginModel(private val mContext: Context) {

    private val TAG = "LoginModel"

    private val loginURL = "https://yiban.stu.edu.cn/web/Account/Login"

    private val yiBanApi: YiBanApi = RetrofitProvider.getYiBanRetrofit(mContext).create(YiBanApi::class.java)

    fun login(account: String, password: String, loginListener: LoginListener) {
        if (!verify(account, password)) {
            loginListener.onFailure(mContext.resources.getString(R.string.login_info_invalid))
            return
        }
        GlobalScope.launch {
            loginListener.onProgress()
            yiBanApi.requestToken
                .enqueue(object : retrofit2.Callback<String> {

                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                        loginListener.onFailure("网络出错，请稍后重试")
                        Log.e(TAG, t.message ?: "")
                    }

                    override fun onResponse(
                        call: retrofit2.Call<String>,
                        response: retrofit2.Response<String>
                    ) {
//                        Log.i(TAG, response.body().toString())
                        // TODO: 2020/12/4 Android 6.0.1会crash
                        val verifyToken = Jsoup.parse(response.body().toString())
                            .getElementsByAttributeValue("name", "__RequestVerificationToken")
                            .first()
                            .attr("value")
                        Log.i(TAG, "login() >>> token is : $verifyToken")
                        yiBanApi.login("$account@stu.edu.cn", password, verifyToken)
                            .enqueue(object : retrofit2.Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
//                                    Log.i(TAG, response.body().toString())
                                    yiBanApi.token.enqueue(object : retrofit2.Callback<YiBanToken> {
                                        override fun onResponse(
                                            call: Call<YiBanToken>,
                                            response: Response<YiBanToken>
                                        ) {
                                            Log.i(TAG, "" + response.body()?.vid)
                                            Log.i(TAG, "" + response.body()?.timestamp)
                                            Log.i(TAG, response.body()?.app ?: "")
                                            Log.i(TAG, response.body()?.nonce ?: "")
                                            Log.i(TAG, response.body()?.token ?: "")
                                            StuContext.getDBService().writeBaseUserInfo(mContext, account, password)
                                            var token = ""
                                            try {
                                                token = URLEncoder.encode(response.body()?.token ?: "", "UTF-8")
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                            yiBanApi.getTimeTable(response.body()?.vid ?: 0, response.body()?.timestamp ?: 0, response.body()?.app, response.body()?.nonce, token).enqueue(object : retrofit2.Callback<YiBanTimeTable> {
                                                override fun onResponse(
                                                    call: Call<YiBanTimeTable>,
                                                    response: Response<YiBanTimeTable>
                                                ) {
                                                    response.body()?.table?.forEach { it ->
                                                        StuContext.getDBService().writeSyllabus(mContext, account, it, SyllabusSourceType.Official)
                                                    }
                                                    StuContext.getSharePreferences(mContext).edit().putString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent").apply()
                                                    loginListener.onSuccess()
                                                }

                                                override fun onFailure(
                                                    call: Call<YiBanTimeTable>,
                                                    t: Throwable
                                                ) {

                                                }
                                            })
                                        }

                                        override fun onFailure(
                                            call: Call<YiBanToken>,
                                            t: Throwable
                                        ) {
                                            Log.e(TAG, t.message ?: "")
                                            loginListener.onFailure("网络出错，请稍后重试")
                                        }
                                    })
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.e(TAG, t.message ?: "")
                                    loginListener.onFailure("网络出错，请稍后重试")
                                }
                            })
                    }

                })
        }
    }

    /**
     * 校验账号、密码的格式是否正确
     */
    private fun verify(account: String, password: String): Boolean {
        if (account.isEmpty()) return false
        if (password.isEmpty()) return false
        return true
    }

//    private suspend fun getToken(): String {
//        var token = ""
//        withContext(Dispatchers.IO) {
//            yiBanApi.requestToken
//                .enqueue(object : retrofit2.Callback<String> {
//
//                    override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(
//                        call: retrofit2.Call<String>,
//                        response: retrofit2.Response<String>
//                    ) {
////                        Log.i(TAG, response.body().toString())
//                        token = Jsoup.parse(response.body().toString())
//                            .getElementsByAttributeValue("name", "__RequestVerificationToken")
//                            .first()
//                            .attr("value")
//                        yiBanApi.login()
//                    }
//
//                })
//        }
////        Log.i(TAG, token)
//        return token
//    }

    interface LoginListener {
        fun onProgress()
        fun onSuccess()
        fun onFailure(msg: String)
    }
}