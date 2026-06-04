package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64; // Adicionado
import android.util.Log;    // Adicionado
import android.view.View;
import android.widget.Button;

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

        // Configuração explícita do click da Área do inscrito
        Button btnAreaInscrito = findViewById(R.id.btn_area_inscrito);
        btnAreaInscrito.setOnClickListener(v -> navegaAreaInscrito());
    }

    public void navega_tela_menu(View v){
        Intent it = new Intent(this, MenuSelecione.class);
        startActivity(it);
    }

    private void navegaAreaInscrito() {
        SharedPreferences prefs = getSharedPreferences("conveniar_prefs", MODE_PRIVATE);
        long userId = prefs.getLong("usuario_id", -1);

        Intent it;
        if (userId != -1) {
            it = new Intent(this, AreaInscrito.class);
        } else {
            it = new Intent(this, CadastroUsuario.class);
        }
        startActivity(it);
    }
}