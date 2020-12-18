package com.stu.syllabuskt.login

import android.content.Context
import android.util.Log
import com.stu.syllabuskt.R
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.api.OfficialAccountApi
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.api.YiBanApi
import com.stu.syllabuskt.bean.AccountNo
import com.stu.syllabuskt.bean.ExpenseRecord
import com.stu.syllabuskt.bean.YiBanTimeTable
import com.stu.syllabuskt.bean.YiBanToken
import com.stu.syllabuskt.db.SyllabusSourceType
import com.stu.syllabuskt.personal.card.AccountAndExpenseData
import com.stu.syllabuskt.personal.card.Type
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

/**
 *yuan
 *2020/9/4
 **/
class YBBusinessModel(private val mContext: Context, private val target: Target) {

    private val TAG = "LoginModel"

    private val loginURL = "https://yiban.stu.edu.cn/web/Account/Login"

    private val yiBanApi: YiBanApi = RetrofitProvider.getYiBanRetrofit(mContext).create(YiBanApi::class.java)
    private val officialAccountApi: OfficialAccountApi = RetrofitProvider.getOfficialWCRetrofit(mContext).create(OfficialAccountApi::class.java)

    fun login(account: String, password: String, ybBusinessListener: YBBusinessListener?, cardBusinessListener: CardBusinessListener? = null, type: Type? = null) {
        if (!verify(account, password)) {
            ybBusinessListener?.onFailure(mContext.resources.getString(R.string.login_info_invalid))
            return
        }
        ybBusinessListener?.onProgress()
        cardBusinessListener?.onProgress()
        yiBanApi.requestToken
            .enqueue(object : retrofit2.Callback<String> {

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    if (target != Target.Card) {
                        loginWithWCOfficialAccountApi(account, password, ybBusinessListener)
                    } else {
                        cardBusinessListener?.onFailure("")
                    }
                    Log.e(TAG, t.message ?: "")
                }

                override fun onResponse(
                    call: retrofit2.Call<String>,
                    response: retrofit2.Response<String>
                ) {
                    val verifyToken = Jsoup.parse(response.body().toString())
                        .getElementsByAttributeValue("name", "__RequestVerificationToken")
                        .first()
                        ?.attr("value")
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
                                        var token = ""
                                        try {
                                            token = URLEncoder.encode(response.body()?.token ?: "", "UTF-8")
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                        if (target == Target.Card) {
                                            val yibanToken = YiBanToken(response.body()?.vid ?: 0, response.body()?.timestamp ?: 0, token, response.body()?.app, response.body()?.nonce)
                                            yiBanApi.getAccountNo(response.body()?.vid ?: 0, response.body()?.timestamp ?: 0, response.body()?.app, token, response.body()?.nonce).enqueue(object : retrofit2.Callback<AccountNo> {
                                                override fun onResponse(
                                                    call: Call<AccountNo>,
                                                    response: Response<AccountNo>
                                                ) {
                                                    val accountNo = AccountNo(response.body()?.accountNo ?: 0, response.body()?.balance ?: 0.0, response.body()?.name ?: "" )
                                                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                                                    var calendar = Calendar.getInstance().apply { add(Calendar.DATE, -3) }
                                                    calendar = when (type) {
                                                        Type.ThreeDays -> Calendar.getInstance().apply { add(Calendar.DATE, -3) }
                                                        Type.OneWeek -> Calendar.getInstance().apply { add(Calendar.DATE, -7) }
                                                        Type.OneMonth -> Calendar.getInstance().apply { add(Calendar.DATE, -30) }
                                                        else -> Calendar.getInstance().apply { add(Calendar.DATE, -3) }
                                                    }
                                                    val start = sdf.format(calendar.time)
                                                    val end = sdf.format(Date())
                                                    yiBanApi.getExpenseRecord(yibanToken.vid, response.body()?.accountNo ?: 0, start, end, yibanToken.timestamp, yibanToken.app, yibanToken.token, yibanToken.nonce).enqueue(object : retrofit2.Callback<List<ExpenseRecord>> {
                                                        override fun onResponse(
                                                            call: Call<List<ExpenseRecord>>,
                                                            response: Response<List<ExpenseRecord>>
                                                        ) {
                                                            cardBusinessListener?.onSuccess(
                                                                AccountAndExpenseData(accountNo, response.body())
                                                            )
                                                        }

                                                        override fun onFailure(
                                                            call: Call<List<ExpenseRecord>>,
                                                            t: Throwable
                                                        ) {
                                                            cardBusinessListener?.onFailure("")
                                                        }
                                                    })
                                                }

                                                override fun onFailure(
                                                    call: Call<AccountNo>,
                                                    t: Throwable
                                                ) {

                                                }
                                            })
                                        } else {
                                            yiBanApi.getTimeTable(response.body()?.vid ?: 0, response.body()?.timestamp ?: 0, response.body()?.app, response.body()?.nonce, token).enqueue(object : retrofit2.Callback<YiBanTimeTable> {
                                                override fun onResponse(
                                                    call: Call<YiBanTimeTable>,
                                                    response: Response<YiBanTimeTable>
                                                ) {
                                                    Thread() {
                                                        if (target == Target.Syllabus) StuContext.getDBService().clearOfficialSyllabusData(mContext)
                                                        else StuContext.getSharePreferences(mContext).edit().putString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent").apply()
                                                        StuContext.getDBService().writeBaseUserInfo(mContext, account, password)
                                                        response.body()?.table?.forEach { it ->
                                                            StuContext.getDBService().writeSyllabus(mContext, account, it, SyllabusSourceType.Official)
                                                        }
                                                        ybBusinessListener?.onSuccess()
                                                    }.run()
                                                }

                                                override fun onFailure(
                                                    call: Call<YiBanTimeTable>,
                                                    t: Throwable
                                                ) {
                                                    if (target != Target.Card) {
                                                        loginWithWCOfficialAccountApi(account, password, ybBusinessListener)
                                                    } else {
                                                        cardBusinessListener?.onFailure("")
                                                    }
                                                }
                                            })
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<YiBanToken>,
                                        t: Throwable
                                    ) {
                                        Log.e(TAG, t.message ?: "")
                                        if (target != Target.Card) {
                                            loginWithWCOfficialAccountApi(account, password, ybBusinessListener)
                                        } else {
                                            cardBusinessListener?.onFailure("")
                                        }
                                    }
                                })
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Log.e(TAG, t.message ?: "")
                                if (target != Target.Card) {
                                    loginWithWCOfficialAccountApi(account, password, ybBusinessListener)
                                } else {
                                    cardBusinessListener?.onFailure("")
                                }
                            }
                        })
                }

            })
    }

    /**
     * 兜底策略：使用另一数据接口实现登录
     */
    private fun loginWithWCOfficialAccountApi(account: String, password: String, loginListener: YBBusinessListener?) {
        officialAccountApi.login(account, password, "登录", "", "")
            .enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (target == Target.Login && response.body().toString().contains("成功")) {
                        StuContext.getDBService().writeBaseUserInfo(mContext, account, password)
                        StuContext.getSharePreferences(mContext).edit().putString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent").apply()
                        loginListener?.onSuccess()
                    } else if (target == Target.Login) {
                        loginListener?.onFailure("账号密码错误或网络状态出错")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    if (target == Target.Login) {
                        loginListener?.onFailure("账号密码错误或网络状态出错")
                    } else {
                        loginListener?.onFailure("网络状态出错，请稍后重试")
                    }
                }
            })
    }

    /**
     * 校验账号、密码的格式是否正确
     */
    private fun verify(account: String, password: String): Boolean {
        if (account.isEmpty() || password.isEmpty()) return false
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

    interface YBBusinessListener {
        fun onProgress()
        fun onSuccess()
        fun onFailure(msg: String)
    }

    interface CardBusinessListener {
        fun onSuccess(accountAndExpenseData: AccountAndExpenseData)
        fun onFailure(msg: String)
        fun onProgress()
    }
}

enum class Target {
    Login,
    Syllabus,
    Card
}