package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

public class CategoriaInscricaoResponse {

    @SerializedName("codCategoriaInscricao")
    private int codCategoriaInscricao;

    @SerializedName("nomeCategoria")
    private String nomeCategoria;

    @SerializedName("dataInicio")
    private String dataInicio;

    @SerializedName("dataFim")
    private String dataFim;

    @SerializedName("valor")
    private double valor;

    public int    getCodCategoriaInscricao() { return codCategoriaInscricao; }
    public String getNomeCategoria()         { return nomeCategoria; }
    public String getDataInicio()            { return dataInicio; }
    public String getDataFim()               { return dataFim; }
    public double getValor()                 { return valor; }
}