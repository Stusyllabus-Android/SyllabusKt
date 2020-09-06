package com.stu.syllabuskt.api

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 *yuan
 *2020/9/5
 **/
object RetrofitProvider {
    private val YIBAN_BASE_URL = "https://yiban.stu.edu.cn/"

    fun getYiBanRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .cookieJar(
                        PersistentCookieJar(
                            SetCookieCache(),
                            SharedPrefsCookiePersistor(context)
                        )
                    )
                    .build())
            .baseUrl(YIBAN_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}