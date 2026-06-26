package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

/** Dados cadastrais completos do usuário do portal de eventos. Schema: UsuarioEventos. */
public class UsuarioEventosResponse {

    @SerializedName("numRegistro")
    private int numRegistro;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    @SerializedName("cracha")
    private String cracha;

    @SerializedName("documento")
    private String documento;

    @SerializedName("tipoDocumentoPessoa")
    private String tipoDocumentoPessoa;

    @SerializedName("sexo")
    private String sexo;

    @SerializedName("telefoneCelular")
    private String telefoneCelular;

    @SerializedName("telefoneCasa")
    private String telefoneCasa;

    @SerializedName("telefoneEmpresa")
    private String telefoneEmpresa;

    @SerializedName("cep")
    private String cep;

    @SerializedName("endereco")
    private String endereco;

    @SerializedName("bairro")
    private String bairro;

    @SerializedName("cidade")
    private String cidade;

    @SerializedName("estado")
    private String estado;

    @SerializedName("pais")
    private String pais;

    public int    getNumRegistro()         { return numRegistro; }
    public String getNome()                { return nome; }
    public String getEmail()               { return email; }
    public String getSenha()               { return senha; }
    public String getCracha()              { return cracha; }
    public String getDocumento()           { return documento; }
    public String getTipoDocumentoPessoa() { return tipoDocumentoPessoa; }
    public String getSexo()               { return sexo; }
    public String getTelefoneCelular()     { return telefoneCelular; }
    public String getTelefoneCasa()        { return telefoneCasa; }
    public String getTelefoneEmpresa()     { return telefoneEmpresa; }
    public String getCep()                 { return cep; }
    public String getEndereco()            { return endereco; }
    public String getBairro()              { return bairro; }
    public String getCidade()              { return cidade; }
    public String getEstado()              { return estado; }
    public String getPais()               { return pais; }
}