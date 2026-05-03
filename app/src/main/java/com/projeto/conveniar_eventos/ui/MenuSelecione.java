package com.projeto.conveniar_eventos.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.adapters.EventoAdapter;
import com.projeto.conveniar_eventos.models.Evento;

import java.util.ArrayList;
import java.util.List;

// tela onde o usuário seleciona uma fundação e visualiza os eventos disponíveis em cada uma
public class MenuSelecione extends AppCompatActivity {
    private EventoAdapter eventoAdapter;
    private List<Evento> listaTeste = new ArrayList<>();
    private RecyclerView rvEventos;

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
        // como ainda não temos conexão com a API, aqui são exemplos de fundações
        String[] listaExemplo = {
                "Selecione",
                "FEPE — Fundação de Apoio ao Ensino, Pesquisa e Extensão",
                "FUNTEF-PR — Fundação de Apoio à UTFPR",
                "FACEV — Fundação de apoio da UFV"
        };

        // usando o adapter pra conseguir modificar a aparência do spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.item_spinner,
                listaExemplo
        );
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinnerFundacoes.setAdapter(spinnerAdapter);

        // o cardview contém a lista de  eventos
        CardView cardListaEventos = findViewById(R.id.card_lista_eventos);
        rvEventos = findViewById(R.id.rv_eventos);
        eventoAdapter = new EventoAdapter(listaTeste);
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setAdapter(eventoAdapter);

        // detecta o item selecionado no spinner pra personalizar o texto exibido na lista
        spinnerFundacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecionado = parent.getItemAtPosition(position).toString();
                listaTeste.clear();
                // usando a FEPE como exemplo
                if (selecionado.equals("FEPE — Fundação de Apoio ao Ensino, Pesquisa e Extensão")) {
                    listaTeste.add(new Evento("CURSO DE ESPECIALIZAÇÃO EM FISIOTERAPIA 2024", "Em andamento"));
                    listaTeste.add(new Evento("Curso de Atualização em Dor Lombar - Turma 15", "Em oferta"));
                    listaTeste.add(new Evento("CROSSROADS 2026 - CONGRESSO INTERNACIONAL DA ASSOCIATION FOR CULTURAL STUDIES (ACS)", "Em oferta"));
                    cardListaEventos.setVisibility(View.VISIBLE);
                } else {
                    // esconde a lista se não tiver selecionada uma opção que tenha eventos
                    cardListaEventos.setVisibility(View.GONE);
                }
                eventoAdapter.notifyDataSetChanged();
            }

            // nada acontece se nenhum item é selecionado
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}