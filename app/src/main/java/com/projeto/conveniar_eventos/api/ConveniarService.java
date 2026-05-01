package com.projeto.conveniar_eventos.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ConveniarService {
    @GET("token/eventos")
    Call<TokenResponse> getToken(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String basicAuth
    );

    @GET("eventos/registro-usuarios")
    Call<List<UsuarioResponse>> buscarUsuarios(
            @Header("X-API-KEY") String apiKey,
            @Header("Authorization") String bearerToken,
            @Query("query") String query,
            @Query("pagina") int pagina,
            @Query("limite") int limite
    );
}