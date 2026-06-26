package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

public class EventoInscricaoResponse {

    @SerializedName("codEventoInscricao")
    private int codEventoInscricao;

    @SerializedName("codEvento")
    private int codEvento;

    @SerializedName("nomeEvento")
    private String nomeEvento;

    @SerializedName("nomeCategoriaInscricao")
    private String nomeCategoriaInscricao;

    @SerializedName("nomeStatus")
    private String nomeStatus;

    @SerializedName("numeroInscricao")
    private int numeroInscricao;

    public int    getCodEventoInscricao()    { return codEventoInscricao; }
    public int    getCodEvento()             { return codEvento; }
    public String getNomeEvento()            { return nomeEvento; }
    public String getNomeCategoriaInscricao(){ return nomeCategoriaInscricao; }
    public String getNomeStatus()            { return nomeStatus; }
    public int    getNumeroInscricao()       { return numeroInscricao; }
}