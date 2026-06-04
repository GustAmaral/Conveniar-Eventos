package com.projeto.conveniar_eventos.api;

import com.google.gson.annotations.SerializedName;

public class UsuarioResponse {
    @SerializedName("numeroRegistro")
    private int numeroRegistro;

    @SerializedName("nomePessoa")
    private String nomePessoa;

    public int getNumeroRegistro() { return numeroRegistro; }
    public String getNomePessoa() { return nomePessoa; }
}