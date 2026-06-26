package com.projeto.conveniar_eventos.api.model;

import com.google.gson.annotations.SerializedName;

/** Documento financeiro de uma inscrição (boleto/pagamento). Schema: EventoDocumento + PagamentoDoc. */
public class EventoDocumentoResponse {

    @SerializedName("codDocFinEvento")
    private int codDocFinEvento;

    @SerializedName("parcela")
    private String parcela;

    @SerializedName("codEvento")
    private int codEvento;

    @SerializedName("tipoDocumento")
    private String tipoDocumento;

    @SerializedName("dataCriacao")
    private String dataCriacao;

    @SerializedName("dataVencimento")
    private String dataVencimento;

    @SerializedName("dataPagamento")
    private String dataPagamento;

    @SerializedName("valor")
    private double valor;

    @SerializedName("valorPago")
    private double valorPago;

    @SerializedName("nomeStatus")
    private String nomeStatus;

    @SerializedName("pagamento")
    private PagamentoDocResponse pagamento;

    public int                getCodDocFinEvento() { return codDocFinEvento; }
    public String             getParcela()         { return parcela; }
    public int                getCodEvento()       { return codEvento; }
    public String             getTipoDocumento()   { return tipoDocumento; }
    public String             getDataCriacao()     { return dataCriacao; }
    public String             getDataVencimento()  { return dataVencimento; }
    public String             getDataPagamento()   { return dataPagamento; }
    public double             getValor()           { return valor; }
    public double             getValorPago()       { return valorPago; }
    public String             getNomeStatus()      { return nomeStatus; }
    public PagamentoDocResponse getPagamento()     { return pagamento; }
}