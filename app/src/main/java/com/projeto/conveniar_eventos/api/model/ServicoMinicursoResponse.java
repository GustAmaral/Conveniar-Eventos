package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

public class ServicoMinicursoResponse {

    @SerializedName("codMiniCurso")
    private int codMiniCurso;

    @SerializedName("nomeMiniCurso")
    private String nomeMiniCurso;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("valor")
    private double valor;

    @SerializedName("numeroVagas")
    private int numeroVagas;

    @SerializedName("situacao")
    private String situacao;

    public int    getCodMiniCurso()  { return codMiniCurso; }
    public String getNomeMiniCurso() { return nomeMiniCurso; }
    public String getDescricao()     { return descricao; }
    public double getValor()         { return valor; }
    public int    getNumeroVagas()   { return numeroVagas; }
    public String getSituacao()      { return situacao; }
}