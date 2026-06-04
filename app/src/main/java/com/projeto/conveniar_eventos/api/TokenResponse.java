package com.projeto.conveniar_eventos.api;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}