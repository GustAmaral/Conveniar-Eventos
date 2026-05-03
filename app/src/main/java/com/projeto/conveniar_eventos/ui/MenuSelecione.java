package com.projeto.conveniar_eventos.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.adapters.EventoAdapter;
import com.projeto.conveniar_eventos.models.Evento;
import java.util.ArrayList;
import java.util.List;

public class MenuSelecione extends AppCompatActivity {
    private EventoAdapter adapter;
    private List<Evento> listaCompleta = new ArrayList<>();
    private List<Evento> listaFiltrada = new ArrayList<>();
    private CardView cardLista;
    private LinearLayout containerFiltros;
    private String statusFiltro = ""; // Variável para guardar o status selecionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selecione);

        // 1. Referências dos Componentes
        EditText editSearch = findViewById(R.id.edit_search);
        ImageButton btnLupa = findViewById(R.id.btn_lupa);
        Button btnExibirFiltros = findViewById(R.id.btn_filtros);
        containerFiltros = findViewById(R.id.container_filtros_extras);
        RadioGroup rgStatus = findViewById(R.id.rg_status);
        cardLista = findViewById(R.id.card_lista_eventos);
        RecyclerView rvEventos = findViewById(R.id.rv_eventos);
        Spinner spinnerFundacao = findViewById(R.id.spinner_fundacoes);

        // 2. Configuração do RecyclerView
        adapter = new EventoAdapter(listaFiltrada);
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setAdapter(adapter);

        // 3. Ação de clicar na Lupa
        btnLupa.setOnClickListener(v -> {
            String texto = editSearch.getText().toString();
            aplicarFiltros(texto);
            fecharTeclado(editSearch);
        });

        // 4. Ação de apertar ENTER no teclado
        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                aplicarFiltros(editSearch.getText().toString());
                fecharTeclado(editSearch);
                return true;
            }
            return false;
        });

        // 5. Lógica dos RadioButtons (Filtro de Status)
        rgStatus.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_andamento) statusFiltro = "Em andamento";
            else if (checkedId == R.id.rb_oferta) statusFiltro = "Em oferta";

            aplicarFiltros(editSearch.getText().toString());
        });

        // 6. Botão Filtros de Busca (Mostrar/Esconder Painel)
        btnExibirFiltros.setOnClickListener(v -> {
            int visibilidade = containerFiltros.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            containerFiltros.setVisibility(visibilidade);
        });

        // 7. Spinner de Fundação
        String[] fundacoes = {"Selecione", "FEPE — Fundação de Apoio ao Ensino, Pesquisa e Extensão", "FUNTEF-PR — Fundação de Apoio à UTFPR ", "FACEV — Fundação de Apoio da UFV"};
        spinnerFundacao.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fundacoes));
        spinnerFundacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                if (pos > 0) {
                    carregarDados();
                    cardLista.setVisibility(View.VISIBLE);
                } else {
                    cardLista.setVisibility(View.GONE);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });
    }

    // MÉTODO QUE ESTAVA FALTANDO / COM NOME DIFERENTE
    private void aplicarFiltros(String texto) {
        listaFiltrada.clear();
        for (Evento e : listaCompleta) {
            boolean matchesTexto = e.getCurso().toLowerCase().contains(texto.toLowerCase());
            boolean matchesStatus = statusFiltro.isEmpty() || e.getSituacao().equalsIgnoreCase(statusFiltro);

            if (matchesTexto && matchesStatus) {
                listaFiltrada.add(e);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void fecharTeclado(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void carregarDados() {
        listaCompleta.clear();
        listaCompleta.add(new Evento("Curso de especialização em fisioterapia", "Em andamento"));
        listaCompleta.add(new Evento("Curso de atualização em dor lombar", "Em oferta"));
        listaCompleta.add(new Evento("CROSSROADS 2026 - Congresso Internacional", "Em oferta"));
        listaCompleta.add(new Evento("Atividade de musculação", "Em andamento"));
        aplicarFiltros(""); // Inicializa a lista mostrando tudo
    }
}