package com.projeto.conveniar_eventos.models;

public class Evento {
    private String curso;
    private String situacao;

    public Evento(String curso, String situacao) {
        this.curso = curso;
        this.situacao = situacao;
    }

    public String getCurso() {
        return curso;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}