package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventoListaResponse {

    @SerializedName("codEvento")
    private int codEvento;

    @SerializedName("codEventoTipo")
    private int codEventoTipo;

    @SerializedName("nomeEvento")
    private String nomeEvento;

    @SerializedName("nomeConvenio")
    private String nomeConvenio;

    @SerializedName("categoria")
    private String categoria;

    @SerializedName("situacao")
    private String situacao;

    @SerializedName("dataInicio")
    private String dataInicio;

    @SerializedName("dataFim")
    private String dataFim;

    @SerializedName("numeroVagas")
    private int numeroVagas;

    @SerializedName("informacoes")
    private List<EventoInformacaoResponse> informacoes;

    @SerializedName("categoriasInscricao")
    private List<CategoriaInscricaoResponse> categoriasInscricao;

    @SerializedName("servicosMinicursos")
    private List<ServicoMinicursoResponse> servicosMinicursos;

    public int    getCodEvento()       { return codEvento; }
    public int    getCodEventoTipo()   { return codEventoTipo; }
    public String getNomeEvento()      { return nomeEvento; }
    public String getNomeConvenio()    { return nomeConvenio; }
    public String getCategoria()       { return categoria; }
    public String getSituacao()        { return situacao; }
    public String getDataInicio()      { return dataInicio; }
    public String getDataFim()         { return dataFim; }
    public int    getNumeroVagas()     { return numeroVagas; }

    public List<EventoInformacaoResponse>    getInformacoes()         { return informacoes; }
    public List<CategoriaInscricaoResponse>  getCategoriasInscricao() { return categoriasInscricao; }
    public List<ServicoMinicursoResponse>    getServicosMinicursos()  { return servicosMinicursos; }

    /**
     * Busca o valor de uma informação pelo nome da chave.
     * As informações extras (local, horário, instrutor, etc.) chegam como
     * lista de pares chave/valor em nomeEventoInformacao / descEventoInformacao.
     */
    public String getInformacao(String chave) {
        if (informacoes == null) return null;
        for (EventoInformacaoResponse info : informacoes) {
            if (chave.equalsIgnoreCase(info.getNomeEventoInformacao())) {
                return info.getDescEventoInformacao();
            }
        }
        return null;
    }

    /** Retorna o valor da primeira categoria de inscrição disponível, ou 0 se não houver. */
    public double getPrimeiroValor() {
        if (categoriasInscricao == null || categoriasInscricao.isEmpty()) return 0;
        return categoriasInscricao.get(0).getValor();
    }
}