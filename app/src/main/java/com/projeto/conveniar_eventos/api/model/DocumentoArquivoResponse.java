package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

/** Arquivo de um documento financeiro de inscrição. Schema: DocumentoEventoArquivo. */
public class DocumentoArquivoResponse {

    @SerializedName("tituloArquivoDocFinEvento")
    private String tituloArquivoDocFinEvento;

    @SerializedName("nomeArquivoDocFinEvento")
    private String nomeArquivoDocFinEvento;

    @SerializedName("descArquivoDocFinEvento")
    private String descArquivoDocFinEvento;

    @SerializedName("codArquivoBinario")
    private int codArquivoBinario;

    @SerializedName("codArquivoDocFinEvento")
    private int codArquivoDocFinEvento;

    public String getTituloArquivoDocFinEvento() { return tituloArquivoDocFinEvento; }
    public String getNomeArquivoDocFinEvento()   { return nomeArquivoDocFinEvento; }
    public String getDescArquivoDocFinEvento()   { return descArquivoDocFinEvento; }
    public int    getCodArquivoBinario()         { return codArquivoBinario; }
    public int    getCodArquivoDocFinEvento()    { return codArquivoDocFinEvento; }
}