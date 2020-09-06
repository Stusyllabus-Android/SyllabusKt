package com.stu.syllabuskt.api;

import com.stu.syllabuskt.bean.YiBanTimeTable;
import com.stu.syllabuskt.bean.YiBanToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * yuan
 * 2020/9/5
 **/
public interface YiBanApi {

    @GET("web/Account/Login")
    Call<String> getRequestToken();

    @FormUrlEncoded
    @POST("web/Account/Login")
    Call<String> login(@Field("Email") String email, @Field("Password") String password, @Field("__RequestVerificationToken") String requestToken);

    @GET("web/Alipay/GetToken")
    Call<YiBanToken> getToken();

    @GET("api/api/creditstudenttimetable/")
    Call<YiBanTimeTable> getTimeTable(@Query("vid") long vid,
                                      @Query("timestamp")long timestamp,
                                      @Query("app") String app,
                                      @Query("nonce") String nonce,
                                      @Query("token") String token);
}
