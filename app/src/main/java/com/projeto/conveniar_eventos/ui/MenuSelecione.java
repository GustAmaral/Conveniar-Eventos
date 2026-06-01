package com.projeto.conveniar_eventos.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.adapters.EventoAdapter;
import com.projeto.conveniar_eventos.models.Evento;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuSelecione extends AppCompatActivity {

    private EventoAdapter adapter;
    private List<Evento> listaCompleta = new ArrayList<>();
    private List<Evento> listaFiltrada = new ArrayList<>();

    private RecyclerView rvEventos;
    private CardView cardLista;
    private LinearLayout containerFiltros;
    private EditText editSearch, editDataFiltro;
    private Spinner spinnerFundacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setNavigationBarColor(Color.parseColor("#DDE2E6"));
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(
                        getWindow(),
                        getWindow().getDecorView()
                );
        controller.setAppearanceLightNavigationBars(true);

        // Deixa o seu XML (activity_menu_selecione) controlar 100% do visual
        setContentView(R.layout.activity_menu_selecione);

        // 1. Vinculação direta dos IDs
        rvEventos = findViewById(R.id.rv_eventos);
        cardLista = findViewById(R.id.card_lista_eventos);
        containerFiltros = findViewById(R.id.container_filtros_extras);
        editSearch = findViewById(R.id.edit_search);
        editDataFiltro = findViewById(R.id.edit_data_filtro);
        spinnerFundacoes = findViewById(R.id.spinner_fundacoes);
        Button btnFiltros = findViewById(R.id.btn_filtros);
        RadioGroup rgStatus = findViewById(R.id.rg_status);

        // 2. Configuração básica da lista
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventoAdapter(listaFiltrada);
        rvEventos.setAdapter(adapter);

        // 3. Spinner padrão (não altera o tamanho definido no seu XML)
        String[] fundacoes = {"Selecione a fundação", "Cientec", "Fundação de Apoio"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fundacoes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFundacoes.setAdapter(spinnerAdapter);

        spinnerFundacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) carregarDados(); // Ativa apenas para Cientec
                else cardLista.setVisibility(View.GONE);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 4. Lógica de expansão do painel cinza
        btnFiltros.setOnClickListener(v -> {
            containerFiltros.setVisibility(containerFiltros.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // 5. Escutadores dos Filtros (Pesquisa e Data)
        TextWatcher filtroWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { aplicarFiltros(); }
            @Override public void afterTextChanged(Editable s) {}
        };
        editSearch.addTextChangedListener(filtroWatcher);
        editDataFiltro.addTextChangedListener(filtroWatcher);
        rgStatus.setOnCheckedChangeListener((group, checkedId) -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        String busca = editSearch.getText().toString().toLowerCase();
        String dataBuscaStr = editDataFiltro.getText().toString();
        int selectedId = ((RadioGroup)findViewById(R.id.rg_status)).getCheckedRadioButtonId();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        listaFiltrada.clear();

        try {
            Date dataLimite = dataBuscaStr.length() == 10 ? sdf.parse(dataBuscaStr) : null;

            for (Evento e : listaCompleta) {
                boolean bateNome = e.getCurso().toLowerCase().contains(busca);
                boolean bateStatus = true;
                if (selectedId == R.id.rb_oferta) bateStatus = e.getSituacao().equalsIgnoreCase("Em oferta");
                else if (selectedId == R.id.rb_andamento) bateStatus = e.getSituacao().equalsIgnoreCase("Em andamento");

                boolean bateData = true;
                if (dataLimite != null) {
                    Date dataE = sdf.parse(e.getDataInicio());
                    bateData = dataE != null && (dataE.after(dataLimite) || dataE.equals(dataLimite));
                }

                if (bateNome && bateStatus && bateData) {
                    listaFiltrada.add(e);
                }
            }
        } catch (Exception e) { Log.e("LOGICA", "Erro de filtro"); }
        adapter.notifyDataSetChanged();
    }

    private void carregarDados() {
        new Thread(() -> {
            try {
                String urlBase = "https://cientec.conveniar.com.br/eventos/";
                Document doc = Jsoup.connect(urlBase).userAgent("Mozilla/5.0").timeout(10000).get();
                Elements linhas = doc.select("#ctl00_ContentPlaceHolder1_ListaCursoUserControl1_gvPrincipal tr");

                List<Evento> novos = new ArrayList<>();

                // Lógica de Datas Variadas (Dia/Mês/Ano)
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Calendar cal = Calendar.getInstance();
                cal.set(2025, Calendar.AUGUST, 5); // Inicia em Agosto/2025

                for (Element linha : linhas) {
                    Elements colunas = linha.select("td");
                    if (colunas.size() >= 3) {
                        String nome = colunas.get(1).text();
                        String status = colunas.get(2).text();
                        String urlFinal = colunas.get(1).select("a").attr("abs:href");

                        // Gera a data e pula 30 dias para cada evento
                        String dataGerada = sdf.format(cal.getTime());
                        cal.add(Calendar.DAY_OF_YEAR, 30);

                        novos.add(new Evento(nome, status, 0, dataGerada, urlFinal));
                    }
                }

                runOnUiThread(() -> {
                    listaCompleta.clear();
                    listaCompleta.addAll(novos);
                    aplicarFiltros();
                    cardLista.setVisibility(View.VISIBLE);
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Erro de conexão", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}