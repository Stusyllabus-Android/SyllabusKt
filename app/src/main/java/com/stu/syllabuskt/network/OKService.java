package com.stu.syllabuskt.network;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

/**
 * yuan
 * 2020/9/5
 **/
public class OKService {

    private static final int TIMEOUT = 20;

    private static OkHttpClient client = null;

    private OKService() {
        super();
    }

//    public static OkHttpClient getClient(Context context) {
//        if (client == null) {
//            synchronized (this) {
//                if (client == null) {
//                    client = new OkHttpClient.Builder()
//                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
//                            .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))
//                            .build();
//                }
//            }
//        }
//        return client;
//    }
}
