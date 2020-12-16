package com.stu.syllabuskt.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Create by yuan on 2020/12/15
 */
interface OfficialAccountApi {

    @FormUrlEncoded
    @POST("wechat/login/login_verify")
    fun login(@Field("ldap_account") account: String,
              @Field("ldap_password") password: String,
              @Field("btn_ok") btn: String,
              @Field("source_type") sourceType: String,
              @Field("openid") openid: String): Call<String>

    @GET("wechat/Credit/Credit_score")
    fun getGrade(): Call<String>

}