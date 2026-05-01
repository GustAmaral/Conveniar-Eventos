package com.projeto.conveniar_eventos.ui;

import android.os.Bundle;
import android.util.Base64; // Adicionado
import android.util.Log;    // Adicionado

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.api.RetrofitClient; // Ajuste conforme seu pacote
import com.projeto.conveniar_eventos.api.TokenResponse;   // Ajuste conforme seu pacote

import retrofit2.Call;     // Adicionado
import retrofit2.Callback; // Adicionado
import retrofit2.Response; // Adicionado

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CONVENIAR_API";
    private final String API_KEY = "78197722-cb8e-430f-8e19-5b7588236def";
    private final String USER = "GrupoEventos";
    private final String PASS = "GrupoEventos@1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Chama a autenticação assim que a tela abre
        solicitarToken();
    }

    private void solicitarToken() {
        String authString = USER + ":" + PASS;
        // O NO_WRAP é essencial para não quebrar o header
        String base64Auth = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP).trim();
        String basicAuth = "Basic " + base64Auth;

        Log.d(TAG, "Iniciando solicitação de token...");

        RetrofitClient.getInstance().getToken(API_KEY, basicAuth).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getAccessToken();
                    Log.d(TAG, "SUCESSO! Token recebido: " + token);
                    // Aqui você poderá chamar a função de buscar usuários futuramente
                } else {
                    Log.e(TAG, "Erro " + response.code() + ": Permissão negada ou credenciais inválidas.");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "Falha crítica na conexão: " + t.getMessage());
            }
        });
    }
}