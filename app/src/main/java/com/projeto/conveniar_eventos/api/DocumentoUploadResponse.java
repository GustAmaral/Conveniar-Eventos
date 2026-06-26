package com.projeto.conveniar_eventos.api;

import com.google.gson.annotations.SerializedName;

public class DocumentoUploadResponse {
    @SerializedName("id")
    public long id;

    @SerializedName("status")
    public String status;

    @SerializedName("mensagem")
    public String mensagem;
}