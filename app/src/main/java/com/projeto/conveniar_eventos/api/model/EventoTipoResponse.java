package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

public class EventoTipoResponse {

    @SerializedName("codEventoTipo")
    private int codEventoTipo;

    @SerializedName("nomeEventoTipo")
    private String nomeEventoTipo;

    public int    getCodEventoTipo()  { return codEventoTipo; }
    public String getNomeEventoTipo() { return nomeEventoTipo; }
}