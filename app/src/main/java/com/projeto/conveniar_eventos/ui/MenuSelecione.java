package com.projeto.conveniar_eventos.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String statusFiltro = "";
    private String dataFiltro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selecione);

        EditText editSearch = findViewById(R.id.edit_search);
        EditText editDataFiltro = findViewById(R.id.edit_data_filtro);
        ImageButton btnLupa = findViewById(R.id.btn_lupa);
        Button btnExibirFiltros = findViewById(R.id.btn_filtros);
        containerFiltros = findViewById(R.id.container_filtros_extras);
        RadioGroup rgStatus = findViewById(R.id.rg_status);
        RadioButton rbAndamento = findViewById(R.id.rb_andamento);
        RadioButton rbOferta = findViewById(R.id.rb_oferta);
        cardLista = findViewById(R.id.card_lista_eventos);
        RecyclerView rvEventos = findViewById(R.id.rv_eventos);
        Spinner spinnerFundacao = findViewById(R.id.spinner_fundacoes);

        adapter = new EventoAdapter(listaFiltrada);
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setAdapter(adapter);

        btnLupa.setOnClickListener(v -> {
            aplicarFiltros(editSearch.getText().toString());
            fecharTeclado(editSearch);
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aplicarFiltros(s.toString());
            }
            public void afterTextChanged(Editable s) {}
        });

        editDataFiltro.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataFiltro = s.toString(); // Atualiza a variável global da data
                aplicarFiltros(editSearch.getText().toString()); // Filtra junto com o que estiver na busca
            }
            public void afterTextChanged(Editable s) {}
        });

        rbAndamento.setOnClickListener(v -> {
            if (statusFiltro.equals("Em andamento")) {
                rgStatus.clearCheck();
                statusFiltro = "";
            } else {
                statusFiltro = "Em andamento";
            }
            aplicarFiltros(editSearch.getText().toString());
        });

        rbOferta.setOnClickListener(v -> {
            if (statusFiltro.equals("Em oferta")) {
                rgStatus.clearCheck();
                statusFiltro = "";
            } else {
                statusFiltro = "Em oferta";
            }
            aplicarFiltros(editSearch.getText().toString());
        });

        btnExibirFiltros.setOnClickListener(v -> {
            containerFiltros.setVisibility(containerFiltros.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });

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

    private void aplicarFiltros(String texto) {
        listaFiltrada.clear();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        for (Evento e : listaCompleta) {
            // 1. Filtro de Texto
            boolean matchesTexto = e.getCurso().toLowerCase().contains(texto.toLowerCase());

            // 2. Filtro de Status
            boolean matchesStatus = statusFiltro.isEmpty() || e.getSituacao().equalsIgnoreCase(statusFiltro);

            // 3. Filtro de Data "A partir de"
            boolean matchesData = true;
            if (!dataFiltro.isEmpty() && dataFiltro.length() == 10) {
                try {
                    java.util.Date dataDigitada = sdf.parse(dataFiltro);
                    java.util.Date dataDoEvento = sdf.parse(e.getDataInicio());

                    // O evento deve ser IGUAL ou DEPOIS da data digitada
                    matchesData = dataDoEvento.after(dataDigitada) || dataDoEvento.equals(dataDigitada);
                } catch (java.text.ParseException err) {
                    // Se a data estiver incompleta ou errada, não filtra por ela ainda
                    matchesData = true;
                }
            }

            // SÓ ADICIONA SE PASSAR NOS 3 FILTROS AO MESMO TEMPO
            if (matchesTexto && matchesStatus && matchesData) {
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
        listaCompleta.add(new Evento("Curso de especialização em fisioterapia", "Em andamento", 40, "10/05/2026"));
        listaCompleta.add(new Evento("Curso de atualização em dor lombar", "Em oferta", 25, "15/06/2026"));
        listaCompleta.add(new Evento("CROSSROADS 2026 - Congresso Internacional", "Em oferta", 10, "20/07/2026"));
        listaCompleta.add(new Evento("Atividade de musculação", "Em andamento", 15, "01/08/2026"));
        aplicarFiltros("");
    }
}