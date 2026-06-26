package com.projeto.conveniar_eventos.api;

import com.google.gson.annotations.SerializedName;

public class UsuarioResponse {

    @SerializedName("numeroRegistro")
    private int numeroRegistro;

    @SerializedName("nomePessoa")
    private String nomePessoa;

    @SerializedName("nomePai")
    private String nomePai;

    @SerializedName("nomeMae")
    private String nomeMae;

    public int    getNumeroRegistro() { return numeroRegistro; }
    public String getNomePessoa()     { return nomePessoa; }
    public String getNomePai()        { return nomePai; }
    public String getNomeMae()        { return nomeMae; }
}