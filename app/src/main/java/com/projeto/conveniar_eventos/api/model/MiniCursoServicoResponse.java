package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

/** Minicurso ou serviço vinculado a uma inscrição de evento. Schema: MiniCursoServico. */
public class MiniCursoServicoResponse {

    @SerializedName("nomeEventoMiniCurso")
    private String nomeEventoMiniCurso;

    @SerializedName("situacao")
    private String situacao;

    @SerializedName("quantidade")
    private int quantidade;

    @SerializedName("valor")
    private double valor;

    public String getNomeEventoMiniCurso() { return nomeEventoMiniCurso; }
    public String getSituacao()            { return situacao; }
    public int    getQuantidade()          { return quantidade; }
    public double getValor()              { return valor; }
}