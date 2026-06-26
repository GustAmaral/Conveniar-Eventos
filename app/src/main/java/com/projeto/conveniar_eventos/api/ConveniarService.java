package com.projeto.conveniar_eventos.api;

import com.projeto.conveniar_eventos.api.model.DocumentoArquivoResponse;
import com.projeto.conveniar_eventos.api.model.EventoArquivoResponse;
import com.projeto.conveniar_eventos.api.model.EventoDocumentoResponse;
import com.projeto.conveniar_eventos.api.model.EventoInscricaoResponse;
import com.projeto.conveniar_eventos.api.model.EventoListaResponse;
import com.projeto.conveniar_eventos.api.model.EventoTipoResponse;
import com.projeto.conveniar_eventos.api.model.MiniCursoServicoResponse;
import com.projeto.conveniar_eventos.api.model.UsuarioEventosResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConveniarService {

    // ── Autenticação ─────────────────────────────────────────────────

    /** Obtém token de acesso ao Portal de Eventos (válido por 2 horas). */
    @GET("token/eventos")
    Call<TokenResponse> getToken(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String basicAuth
    );

    // ── Tipos de evento ──────────────────────────────────────────────

    /** Lista todos os tipos de eventos cadastrados. */
    @GET("eventos/tipos")
    Call<List<EventoTipoResponse>> getTiposEvento(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken
    );

    // ── Eventos ──────────────────────────────────────────────────────

    /**
     * Lista eventos com filtros opcionais.
     * @param query    Filtra por nome, dataInicio, dataFim, convênio ou categoria.
     * @param pagina   Página atual (mínimo 1, padrão 1).
     * @param limite   Registros por página (máximo 1000, padrão 50).
     * @param ordenacao Critério de ordenação: "nomeEvento:asc" ou "nomeEvento:desc".
     */
    @GET("eventos")
    Call<List<EventoListaResponse>> getEventos(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Query("query")     String query,
            @Query("pagina")    int pagina,
            @Query("limite")    int limite,
            @Query("ordenacao") String ordenacao
    );

    /**
     * Lista eventos por array de IDs.
     * @param codEventos Lista de códigos dos eventos a buscar.
     */
    @GET("eventos/ids")
    Call<List<EventoListaResponse>> getEventosPorIds(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Query("codEventos") List<String> codEventos
    );

    /**
     * Lista os arquivos (materiais) de um evento específico.
     * @param codEvento Código do evento.
     */
    @GET("eventos/{codEvento}/arquivos")
    Call<List<EventoArquivoResponse>> getArquivosEvento(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Path("codEvento") int codEvento,
            @Query("pagina")   int pagina,
            @Query("limite")   int limite
    );

    // ── Inscrições ───────────────────────────────────────────────────

    /**
     * Lista todas as inscrições do usuário autenticado.
     * @param ordenacao Critério de ordenação: "nomeEvento:asc" ou "nomeEvento:desc".
     */
    @GET("eventos/inscricoes")
    Call<List<EventoInscricaoResponse>> getInscricoes(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Query("pagina")    int pagina,
            @Query("limite")    int limite,
            @Query("ordenacao") String ordenacao
    );

    /**
     * Lista os arquivos de uma inscrição específica.
     * @param codEventoInscricao Código da inscrição.
     */
    @GET("eventos/inscricao/{codEventoInscricao}/arquivos")
    Call<List<EventoArquivoResponse>> getArquivosInscricao(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Path("codEventoInscricao") int codEventoInscricao,
            @Query("pagina")            int pagina,
            @Query("limite")            int limite
    );

    /**
     * Lista os documentos financeiros (boletos) de uma inscrição.
     * @param codEventoInscricao Código da inscrição.
     */
    @GET("eventos/inscricao/{codEventoInscricao}/documentos")
    Call<List<EventoDocumentoResponse>> getDocumentosInscricao(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Path("codEventoInscricao") int codEventoInscricao,
            @Query("pagina")            int pagina,
            @Query("limite")            int limite
    );

    /**
     * Lista os arquivos de um documento financeiro específico.
     * @param codDocumento Código do documento financeiro.
     */
    @GET("eventos/documentos/{codDocumento}/arquivos")
    Call<List<DocumentoArquivoResponse>> getArquivosDocumento(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Path("codDocumento") int codDocumento,
            @Query("pagina")      int pagina,
            @Query("limite")      int limite
    );

    /**
     * Lista os minicursos/serviços vinculados a uma inscrição.
     * @param codEventoInscricao Código da inscrição.
     */
    @GET("eventos/minicurso-servico/{codEventoInscricao}")
    Call<List<MiniCursoServicoResponse>> getMinicursosServicos(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Path("codEventoInscricao") int codEventoInscricao,
            @Query("pagina")            int pagina,
            @Query("limite")            int limite
    );

    // ── Registro de usuários ─────────────────────────────────────────

    /**
     * Busca registros de usuários por filtro de texto.
     * @param query Filtra por nomePessoa, CPF/CNPJ/Passaporte ou identidade.
     */
    @GET("eventos/registro-usuarios")
    Call<List<UsuarioResponse>> buscarUsuarios(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Query("query")     String query,
            @Query("pagina")    int pagina,
            @Query("limite")    int limite,
            @Query("ordenacao") String ordenacao
    );

    // ── Usuário do portal ────────────────────────────────────────────

    /** Retorna os dados cadastrais do usuário autenticado. */
    @GET("eventos/usuario")
    Call<UsuarioEventosResponse> getUsuario(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken
    );

    /** Cadastra um novo usuário no portal de eventos. */
    @POST("eventos/usuario")
    Call<Void> cadastrarUsuario(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Body UsuarioEventosResponse usuario
    );

    /** Atualiza os dados do usuário autenticado. */
    @PUT("eventos/usuario")
    Call<Void> atualizarUsuario(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Body UsuarioEventosResponse usuario
    );

    // ── TODO: descomentar quando a API estiver disponível ────────────
    // @Multipart
    // @POST("inscricoes/{id}/documentos")
    // Call<DocumentoUploadResponse> uploadDocumento(
    //         @Header("Authorization") String bearerToken,
    //         @Path("id") long inscricaoId,
    //         @Part("tipo_documento") RequestBody tipo,
    //         @Part MultipartBody.Part arquivo
    // );
}
