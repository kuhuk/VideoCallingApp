package com.sharma.kuhuk.videochat;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    @SerializedName("sessionId")
    private String sessionId;

    @SerializedName("token")
    private String token;

    public ServerResponse (String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
