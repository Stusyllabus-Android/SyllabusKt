package com.stu.syllabuskt

import com.stu.syllabuskt.api.OwnApi
import com.stu.syllabuskt.api.RetrofitProvider
import com.stu.syllabuskt.bean.VersionInfo
import retrofit2.Call
import retrofit2.Response

/**
 * Create by yuan on 2020/12/19
 */
class CheckUpdateModel {

    private val ownApi: OwnApi = RetrofitProvider.getOwnRetrofit().create(OwnApi::class.java)

    /**
     * 根据VersionName来判断是否可以更新，忽略被灰度命中的用户
     */
    fun canUpdateWithVersionName(listener: UpdateListener) {
        val result = Result(false, null)
        ownApi.checkoutUpdate().enqueue(object : retrofit2.Callback<VersionInfo> {
            override fun onResponse(call: Call<VersionInfo>, response: Response<VersionInfo>) {
                response.body()?.let {
                    val serverInfo = it.version_name.split(".")
                    val localInfo = App.versionName.split(".")
                    if (serverInfo[0] > localInfo[0] || (serverInfo[0] == localInfo[0] && serverInfo[1] > localInfo[1])) {
                        result.canUpdate = true
                    }
                    result.versionInfo = it
                }
                listener.canUpdate(result)
            }

            override fun onFailure(call: Call<VersionInfo>, t: Throwable) {
                listener.canUpdate(result)
            }
        })
    }

    /**
     * 根据VersionCode来判断是否可以更新，面向全体用户
     */
    fun canUpdateWithVersionCode(listener: UpdateListener) {
        val result = Result(false, null)
        ownApi.checkoutUpdate().enqueue(object : retrofit2.Callback<VersionInfo> {
            override fun onResponse(call: Call<VersionInfo>, response: Response<VersionInfo>) {
                response.body()?.let {
                    if (it.version_code > App.versionCode) {
                        result.canUpdate = true
                    }
                    result.versionInfo = it
                }
                listener.canUpdate(result)
            }

            override fun onFailure(call: Call<VersionInfo>, t: Throwable) {
                listener.canUpdate(result)
            }
        })
    }

    interface UpdateListener {
        fun canUpdate(result: Result)
    }
}

data class Result(var canUpdate: Boolean, var versionInfo: VersionInfo?)