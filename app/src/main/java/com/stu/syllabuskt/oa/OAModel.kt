package com.stu.syllabuskt.oa

import android.content.Context
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.api.YiBanApi
import com.stu.syllabuskt.bean.OABean
import retrofit2.Call
import retrofit2.Response

/**
 * Create by yuan on 2020/12/5
 */
class OAModel(private val mContext: Context) {

    private val yiBanApi: YiBanApi = RetrofitProvider.getYiBanRetrofit(mContext).create(YiBanApi::class.java)

    fun getOAList(oaModelListener: OAModelListener) {
        oaModelListener.onProgress()
        yiBanApi.getOAList(1, 10, -1)
            .enqueue(object : retrofit2.Callback<List<OABean>> {
                override fun onFailure(call: Call<List<OABean>>, t: Throwable) {
                    oaModelListener.onFailure(t.message ?: "")
                }

                override fun onResponse(
                    call: Call<List<OABean>>,
                    response: Response<List<OABean>>
                ) {
                    oaModelListener.onSuccess(response.body())
                }
            })
    }

    interface OAModelListener {
        fun onProgress()
        fun onSuccess(oaList: List<OABean>?)
        fun onFailure(msg: String)
    }

}