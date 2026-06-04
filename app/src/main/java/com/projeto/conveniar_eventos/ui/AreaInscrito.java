package com.projeto.conveniar_eventos.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.DatabaseHelper;

import java.util.ArrayList;

public class AreaInscrito extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_inscrito);

        TextView tvBoasVindas = findViewById(R.id.tv_boas_vindas);
        ListView lvInscricoes = findViewById(R.id.lv_inscricoes);
        TextView tvVazio       = findViewById(R.id.tv_lista_vazia);

        String nome = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getString("usuario_nome", "Usuário");
        long userId = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getLong("usuario_id", -1);

        tvBoasVindas.setText("Painel de: " + nome);

        ArrayList<String> dadosInscricoes = new ArrayList<>();
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Cursor c = db.getInscricoesUsuario(userId);

        if (c != null) {
            while (c.moveToNext()) {
                int evId = c.getInt(0);
                String data = c.getString(1);
                dadosInscricoes.add("Curso ID: " + evId + " | Inscrito em: " + data);
            }
            c.close();
        }

        if (dadosInscricoes.isEmpty()) {
            tvVazio.setVisibility(View.VISIBLE);
            lvInscricoes.setVisibility(View.GONE);
        } else {
            tvVazio.setVisibility(View.GONE);
            lvInscricoes.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dadosInscricoes);
            lvInscricoes.setAdapter(adapter);
        }
    }
}