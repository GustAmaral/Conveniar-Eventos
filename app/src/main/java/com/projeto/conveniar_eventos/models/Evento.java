package com.projeto.conveniar_eventos.models;

/**
 * Modelo de Evento.
 *
 * Campos atuais refletem o que o mock (e futuramente a API) fornece.
 * Quando a API estiver integrada, adicione aqui os campos extras
 * que ela retornar (ex: cargaHoraria, valor, local, etc).
 */
public class Evento {

    // ── Tipos de evento ──────────────────────────────────────────────
    public static final String TIPO_GESTAO       = "GESTAO";
    public static final String TIPO_TI           = "TI";
    public static final String TIPO_JURIDICO     = "JURIDICO";
    public static final String TIPO_COMUNICACAO  = "COMUNICACAO";

    // ── Campos base ──────────────────────────────────────────────────
    private final int    id;
    private final String curso;
    private final String situacao;
    private       int    vagas;          // mutável: diminui a cada inscrição
    private final String dataInicio;
    private final String dataFim;
    private final String horario;        // ex: "08h00 às 12h00"
    private final String local;          // ex: "Online" ou "Sede Cientec – BH"
    private final double valor;          // R$; 0 = gratuito
    private final int    cargaHoraria;   // horas
    private final String instrutor;
    private final String descricao;
    private final String tipoEvento;     // usa as constantes acima
    private final String urlDetalhes;

    public Evento(int id, String curso, String situacao, int vagas,
                  String dataInicio, String dataFim, String horario,
                  String local, double valor, int cargaHoraria,
                  String instrutor, String descricao,
                  String tipoEvento, String urlDetalhes) {
        this.id           = id;
        this.curso        = curso;
        this.situacao     = situacao;
        this.vagas        = vagas;
        this.dataInicio   = dataInicio;
        this.dataFim      = dataFim;
        this.horario      = horario;
        this.local        = local;
        this.valor        = valor;
        this.cargaHoraria = cargaHoraria;
        this.instrutor    = instrutor;
        this.descricao    = descricao;
        this.tipoEvento   = tipoEvento;
        this.urlDetalhes  = urlDetalhes;
    }

    // ── Getters ──────────────────────────────────────────────────────
    public int    getId()           { return id; }
    public String getCurso()        { return curso; }
    public String getSituacao()     { return situacao; }
    public int    getVagas()        { return vagas; }
    public String getDataInicio()   { return dataInicio; }
    public String getDataFim()      { return dataFim; }
    public String getHorario()      { return horario; }
    public String getLocal()        { return local; }
    public double getValor()        { return valor; }
    public int    getCargaHoraria() { return cargaHoraria; }
    public String getInstrutor()    { return instrutor; }
    public String getDescricao()    { return descricao; }
    public String getTipoEvento()   { return tipoEvento; }
    public String getUrlDetalhes()  { return urlDetalhes; }

    /** Define vagas (usado pelo MockRepository para sincronizar com o banco). */
    public void setVagas(int vagas) { this.vagas = vagas; }

    /** Diminui as vagas em 1. Chamado após inscrição confirmada. */
    public void decrementarVaga() {
        if (vagas > 0) vagas--;
    }
}