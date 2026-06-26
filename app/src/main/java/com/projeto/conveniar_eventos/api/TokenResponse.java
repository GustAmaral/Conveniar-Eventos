package com.projeto.conveniar_eventos.api;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("acessToken")
    private String acessToken;

    @SerializedName("expires")
    private int expires;

    public String getAcessToken() { return acessToken; }
    public int    getExpires()    { return expires; }
}