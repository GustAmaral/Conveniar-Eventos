package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

/** Cobre EventoArquivo (arquivos do evento) e EventoInscricaoArquivo (arquivos da inscrição). */
public class EventoArquivoResponse {

    // ── Campos de EventoArquivo (/eventos/{id}/arquivos) ─────────────
    @SerializedName("codArquivoEvento")
    private int codArquivoEvento;

    @SerializedName("nomeArquivoEvento")
    private String nomeArquivoEvento;

    @SerializedName("tituloArquivoEvento")
    private String tituloArquivoEvento;

    @SerializedName("codEvento")
    private int codEvento;

    // ── Campos de EventoInscricaoArquivo (/eventos/inscricao/{id}/arquivos) ──
    @SerializedName("codArquivoEventoInscricao")
    private int codArquivoEventoInscricao;

    @SerializedName("nomeArquivoEventoInscricao")
    private String nomeArquivoEventoInscricao;

    @SerializedName("tituloArquivoEventoInscricao")
    private String tituloArquivoEventoInscricao;

    @SerializedName("descArquivoEventoInscricao")
    private String descArquivoEventoInscricao;

    // ── Campo compartilhado ──────────────────────────────────────────
    @SerializedName("codArquivoBinario")
    private int codArquivoBinario;

    public int    getCodArquivoEvento()              { return codArquivoEvento; }
    public String getNomeArquivoEvento()             { return nomeArquivoEvento; }
    public String getTituloArquivoEvento()           { return tituloArquivoEvento; }
    public int    getCodEvento()                     { return codEvento; }
    public int    getCodArquivoEventoInscricao()     { return codArquivoEventoInscricao; }
    public String getNomeArquivoEventoInscricao()    { return nomeArquivoEventoInscricao; }
    public String getTituloArquivoEventoInscricao()  { return tituloArquivoEventoInscricao; }
    public String getDescArquivoEventoInscricao()    { return descArquivoEventoInscricao; }
    public int    getCodArquivoBinario()             { return codArquivoBinario; }
}