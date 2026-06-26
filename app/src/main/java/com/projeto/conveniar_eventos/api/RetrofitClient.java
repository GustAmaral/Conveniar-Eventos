package com.projeto.conveniar_eventos.api;

import com.projeto.conveniar_eventos.BuildConfig;
import com.projeto.conveniar_eventos.util.MockApiInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.conveniar.com.br/";
    private static Retrofit retrofit;

    public static ConveniarService getInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            if (BuildConfig.USE_MOCK_API) {
                clientBuilder.addInterceptor(new MockApiInterceptor());
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ConveniarService.class);
    }

    /** Força recriação do cliente — útil ao trocar entre mock e produção em runtime de testes. */
    public static void resetar() {
        retrofit = null;
    }
}