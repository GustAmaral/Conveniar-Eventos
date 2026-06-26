package com.projeto.conveniar_eventos.models;

import java.util.Collections;
import java.util.List;

public class Evento {

    // ── Tipos de evento ──────────────────────────────────────────────
    public static final String TIPO_GESTAO       = "GESTAO";
    public static final String TIPO_TI           = "TI";
    public static final String TIPO_JURIDICO     = "JURIDICO";
    public static final String TIPO_COMUNICACAO  = "COMUNICACAO";

    // ── Tipos de documento ───────────────────────────────────────────
    public static final String DOC_CPF                = "CPF";
    public static final String DOC_DIPLOMA            = "DIPLOMA";
    public static final String DOC_OAB                = "OAB";
    public static final String DOC_COMPROVANTE_VINCULO = "COMPROVANTE_VINCULO";
    public static final String DOC_CURRICULO          = "CURRICULO";

    // ── Campos base ──────────────────────────────────────────────────
    private final int          id;
    private final String       curso;
    private final String       situacao;
    private       int          vagas;
    private final String       dataInicio;
    private final String       dataFim;
    private final String       horario;
    private final String       local;
    private final double       valor;
    private final int          cargaHoraria;
    private final String       instrutor;
    private final String       descricao;
    private final String       tipoEvento;
    private final String       fundacao;
    private final List<String> documentosRequeridos;

    public Evento(int id, String curso, String situacao, int vagas,
                  String dataInicio, String dataFim, String horario,
                  String local, double valor, int cargaHoraria,
                  String instrutor, String descricao,
                  String tipoEvento, String fundacao,
                  List<String> documentosRequeridos) {
        this.id                   = id;
        this.curso                = curso;
        this.situacao             = situacao;
        this.vagas                = vagas;
        this.dataInicio           = dataInicio;
        this.dataFim              = dataFim;
        this.horario              = horario;
        this.local                = local;
        this.valor                = valor;
        this.cargaHoraria         = cargaHoraria;
        this.instrutor            = instrutor;
        this.descricao            = descricao;
        this.tipoEvento           = tipoEvento;
        this.fundacao             = fundacao;
        this.documentosRequeridos = documentosRequeridos != null
                ? Collections.unmodifiableList(documentosRequeridos)
                : Collections.emptyList();
    }

    // ── Getters ──────────────────────────────────────────────────────
    public int          getId()                   { return id; }
    public String       getCurso()                { return curso; }
    public String       getSituacao()             { return situacao; }
    public int          getVagas()                { return vagas; }
    public String       getDataInicio()           { return dataInicio; }
    public String       getDataFim()              { return dataFim; }
    public String       getHorario()              { return horario; }
    public String       getLocal()                { return local; }
    public double       getValor()                { return valor; }
    public int          getCargaHoraria()         { return cargaHoraria; }
    public String       getInstrutor()            { return instrutor; }
    public String       getDescricao()            { return descricao; }
    public String       getTipoEvento()           { return tipoEvento; }
    public String       getFundacao()             { return fundacao; }
    public List<String> getDocumentosRequeridos() { return documentosRequeridos; }

    /** Define vagas (usado pelo MockRepository para sincronizar com o banco). */
    public void setVagas(int vagas) { this.vagas = vagas; }

    /** Diminui as vagas em 1. Chamado após inscrição confirmada. */
    public void decrementarVaga() {
        if (vagas > 0) vagas--;
    }
}