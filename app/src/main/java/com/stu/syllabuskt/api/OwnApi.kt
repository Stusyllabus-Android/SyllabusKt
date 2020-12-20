package com.stu.syllabuskt.api

import com.stu.syllabuskt.bean.VersionInfo
import com.stu.syllabuskt.personal.setting.FeedbackCallBack
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Create by yuan on 2020/12/19
 */
interface OwnApi {

    @FormUrlEncoded
    @POST("feedback/insert")
    fun postFeedback(
        @Field("user") user: String,
        @Field("description") description: String,
        @Field("version_code") versionCode: Int,
        @Field("phone_system") sys: String
    ): Call<FeedbackCallBack>

    @GET("versioninfo")
    fun checkoutUpdate(): Call<VersionInfo>
}