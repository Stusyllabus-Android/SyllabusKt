package com.stu.syllabuskt.bean;

import com.google.gson.annotations.SerializedName;

/**
 * yuan
 * 2020/9/5
 **/
public class YiBanToken {
    /**
     * vid : 157337
     * timestamp : 1573560742
     * token : IM37szymlVvUljCKnWDUBGYpYS5WxuzSRFwAIHIBuio=
     * app : stu.web
     * nonce : 53dc5c7d-e84f-45a9-88ec-15e86beca0c7
     */

    @SerializedName("vid")
    public long vid;
    @SerializedName("timestamp")
    public long timestamp;
    @SerializedName("token")
    public String token;
    @SerializedName("app")
    public String app;
    @SerializedName("nonce")
    public String nonce;


    public YiBanToken(long vid, long timestamp, String token, String app, String nonce) {
        super();
        this.vid = vid;
        this.timestamp = timestamp;
        this.token = token;
        this.app = app;
        this.nonce = nonce;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "YiBanToken{" +
                "vid=" + vid +
                ", timestamp=" + timestamp +
                ", token='" + token + '\'' +
                ", app='" + app + '\'' +
                ", nonce='" + nonce + '\'' +
                '}';
    }
}
