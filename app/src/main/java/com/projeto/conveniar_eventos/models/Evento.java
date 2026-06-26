package com.projeto.conveniar_eventos.models;

import com.projeto.conveniar_eventos.api.model.EventoListaResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    // ── Mapeamento da API ────────────────────────────────────────────

    // codEventoTipo da API → constante local de tipoEvento
    private static final Map<Integer, String> TIPO_POR_COD;
    static {
        Map<Integer, String> m = new HashMap<>();
        m.put(1, TIPO_GESTAO);
        m.put(2, TIPO_TI);
        m.put(3, TIPO_JURIDICO);
        m.put(4, TIPO_COMUNICACAO);
        TIPO_POR_COD = Collections.unmodifiableMap(m);
    }

    // Documentos requeridos por tipo de evento
    public static final Map<String, List<String>> DOCS_POR_TIPO;
    static {
        Map<String, List<String>> m = new HashMap<>();
        m.put(TIPO_GESTAO,      Arrays.asList(DOC_CPF, DOC_COMPROVANTE_VINCULO));
        m.put(TIPO_TI,          Arrays.asList(DOC_CPF, DOC_CURRICULO));
        m.put(TIPO_JURIDICO,    Arrays.asList(DOC_CPF, DOC_OAB, DOC_DIPLOMA));
        m.put(TIPO_COMUNICACAO, Arrays.asList(DOC_CPF));
        DOCS_POR_TIPO = Collections.unmodifiableMap(m);
    }

    /** Retorna a lista de documentos requeridos para um tipo de evento. */
    public static List<String> docsPorTipo(String tipoEvento) {
        return DOCS_POR_TIPO.getOrDefault(tipoEvento, Collections.emptyList());
    }

    private static final SimpleDateFormat FMT_API  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat FMT_LOCAL = new SimpleDateFormat("dd/MM/yyyy",            Locale.getDefault());

    /**
     * Converte um {@link EventoListaResponse} (schema da API) para um {@link Evento} local.
     *
     * Detalhes como local, horário, instrutor, carga horária e descrição chegam como
     * pares chave/valor dentro de {@code informacoes[]} e são extraídos pelo helper
     * {@link EventoListaResponse#getInformacao(String)}.
     */
    public static Evento fromApi(EventoListaResponse r) {
        String tipo     = TIPO_POR_COD.getOrDefault(r.getCodEventoTipo(), TIPO_GESTAO);
        String dataIni  = formatarData(r.getDataInicio());
        String dataFim  = formatarData(r.getDataFim());

        String cargaStr = r.getInformacao("cargaHoraria");
        int cargaHoraria = 0;
        if (cargaStr != null) {
            try { cargaHoraria = Integer.parseInt(cargaStr.trim()); } catch (NumberFormatException ignored) {}
        }

        List<String> docs = DOCS_POR_TIPO.getOrDefault(tipo, Collections.emptyList());

        return new Evento(
                r.getCodEvento(),
                nvl(r.getNomeEvento(),   "Sem nome"),
                nvl(r.getSituacao(),     "Em oferta"),
                r.getNumeroVagas(),
                dataIni,
                dataFim,
                nvl(r.getInformacao("horario"),   ""),
                nvl(r.getInformacao("local"),      ""),
                r.getPrimeiroValor(),
                cargaHoraria,
                nvl(r.getInformacao("instrutor"),  ""),
                nvl(r.getInformacao("descricao"),  ""),
                tipo,
                nvl(r.getNomeConvenio(), ""),
                docs
        );
    }

    private static String formatarData(String iso) {
        if (iso == null || iso.isEmpty()) return "";
        try {
            Date d = FMT_API.parse(iso);
            return d != null ? FMT_LOCAL.format(d) : iso;
        } catch (ParseException e) {
            return iso;
        }
    }

    private static String nvl(String value, String fallback) {
        return (value != null && !value.isEmpty()) ? value : fallback;
    }
}