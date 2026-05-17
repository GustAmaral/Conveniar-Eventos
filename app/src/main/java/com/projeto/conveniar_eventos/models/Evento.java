package com.projeto.conveniar_eventos.models;

public class Evento {
    private String curso;
    private String situacao;
    private Integer vagas;
    private String dataInicio;
    private String urlDetalhes; // Usado para navegação

    public Evento(String curso, String situacao, Integer vagas, String dataInicio, String urlDetalhes) {
        this.curso = curso;
        this.situacao = situacao;
        this.vagas = vagas;
        this.dataInicio = dataInicio;
        this.urlDetalhes = urlDetalhes;
    }

    public String getCurso() { return curso; }
    public String getSituacao() { return situacao; }
    public Integer getVagas() { return vagas; }
    public String getDataInicio() { return dataInicio; }
    public String getUrlDetalhes() { return urlDetalhes; }
}