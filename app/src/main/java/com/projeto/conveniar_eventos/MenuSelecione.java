package com.projeto.conveniar_eventos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuSelecione extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_selecione);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinnerFundacoes = findViewById(R.id.spinner_fundacoes);
        // como ainda não temos conexão com a API deixei só exemplos
        String[] listaExemplo = {
                "Selecione",
                "FEPE — Fundação de Apoio ao Ensino, Pesquisa e Extensão",
                "FUNTEF-PR — Fundação de Apoio à UTFPR",
                "FACEV — Fundação de apoio da UFV"
        };

        // usando o Adapter pra conseguir modificar o padrao de texto do spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_spinner,
                listaExemplo
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFundacoes.setAdapter(adapter);
    }
}