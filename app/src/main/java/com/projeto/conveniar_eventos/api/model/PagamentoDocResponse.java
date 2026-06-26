package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

public class PagamentoDocResponse {

    @SerializedName("nomeTipoPagamento")
    private String nomeTipoPagamento;

    @SerializedName("codBoleto")
    private int codBoleto;

    @SerializedName("titulo")
    private String titulo;

    @SerializedName("instrucoes")
    private String instrucoes;

    public String getNomeTipoPagamento() { return nomeTipoPagamento; }
    public int    getCodBoleto()         { return codBoleto; }
    public String getTitulo()            { return titulo; }
    public String getInstrucoes()        { return instrucoes; }
}