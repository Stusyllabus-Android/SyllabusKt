package com.stu.syllabuskt.login

import android.content.Context
import android.util.Log
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.api.YiBanApi
import com.stu.syllabuskt.bean.YiBanToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response

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
                        Log.e(TAG, t.message ?: "")
                    }

                    override fun onResponse(
                        call: retrofit2.Call<String>,
                        response: retrofit2.Response<String>
                    ) {
//                        Log.i(TAG, response.body().toString())
                        val token = Jsoup.parse(response.body().toString())
                            .getElementsByAttributeValue("name", "__RequestVerificationToken")
                            .first()
                            .attr("value")
                        Log.i(TAG, token)
                        yiBanApi.login("$account@stu.edu.cn", password, token)
                            .enqueue(object : retrofit2.Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
//                                    Log.i(TAG, response.body().toString())
                                    StuContext.getDBService().writeUserAccount(mContext, account)
                                    StuContext.getDBService().writeUserPassword(mContext, password)
                                    loginListener.onSuccess()
//                                    yiBanApi.token.enqueue(object : retrofit2.Callback<YiBanToken> {
//                                        override fun onResponse(
//                                            call: Call<YiBanToken>,
//                                            response: Response<YiBanToken>
//                                        ) {
//                                            StuContext.getDBService().writeUserAccount(mContext, account)
//                                            StuContext.getDBService().writeUserPassword(mContext, password)
//                                            Log.i(TAG, "" + response.body()?.vid ?: "")
//                                            Log.i(TAG, "" + response.body()?.timestamp ?: "")
//                                            Log.i(TAG, response.body()?.app ?: "")
//                                            Log.i(TAG, response.body()?.nonce ?: "")
//                                            Log.i(TAG, response.body()?.token ?: "")
//                                        }
//
//                                        override fun onFailure(
//                                            call: Call<YiBanToken>,
//                                            t: Throwable
//                                        ) {
//                                            Log.e(TAG, t.message ?: "")
//                                        }
//                                    })
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.e(TAG, t.message ?: "")
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