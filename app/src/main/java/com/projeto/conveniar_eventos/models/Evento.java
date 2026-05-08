package com.projeto.conveniar_eventos.models;

public class Evento {
    private String curso;
    private String situacao;
    private Integer vagas;

    public Evento(String curso, String situacao, Integer vagas) {
        this.curso = curso;
        this.situacao = situacao;
        this.vagas = vagas;
    }

    public String getCurso() {
        return curso;
    }
    public String getSituacao() {
        return situacao;
    }
    public Integer getVagas(){
        return vagas;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
}