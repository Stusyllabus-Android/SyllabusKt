package com.stu.syllabuskt.oa

import android.content.Context
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.api.YiBanApi
import retrofit2.Call
import retrofit2.Response

/**
 * Create by yxbao on 2020/12/13
 */
class OADetailModel(private val mContext: Context) {

    private val yiBanApi: YiBanApi = RetrofitProvider.getYiBanRetrofit(mContext).create(YiBanApi::class.java)

    fun getOADetail(id: Int, oaDetailModelListener: OADetailModelListener) {
        oaDetailModelListener.onProgress()
        yiBanApi.getOADetail(id.toLong())
            .enqueue(object : retrofit2.Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    oaDetailModelListener.onFailure(t.message ?: "")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    oaDetailModelListener.onSuccess(response.body())
                }
            })
    }

    interface OADetailModelListener {
        fun onProgress()
        fun onSuccess(content: String?)
        fun onFailure(msg: String)
    }

}