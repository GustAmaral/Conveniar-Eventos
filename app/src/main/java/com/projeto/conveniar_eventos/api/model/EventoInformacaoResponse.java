package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

public class EventoInformacaoResponse {

    @SerializedName("nomeEventoInformacao")
    private String nomeEventoInformacao;

    @SerializedName("descEventoInformacao")
    private String descEventoInformacao;

    public String getNomeEventoInformacao() { return nomeEventoInformacao; }
    public String getDescEventoInformacao() { return descEventoInformacao; }
}