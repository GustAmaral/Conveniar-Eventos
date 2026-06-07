package com.projeto.conveniar_eventos.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.adapters.EventoAdapter;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuSelecione extends AppCompatActivity {

    private EventoAdapter adapter;
    private final List<Evento> listaCompleta = new ArrayList<>();
    private final List<Evento> listaFiltrada = new ArrayList<>();

    private RecyclerView rvEventos;
    private CardView cardLista;
    private LinearLayout containerFiltros;
    private EditText editSearch, editDataFiltro;
    private RadioGroup rgStatus;
    private RadioButton rbAndamento, rbOferta;
    private Spinner spinnerFundacoes; // Escopo alterado para global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setNavigationBarColor(Color.parseColor("#DDE2E6"));
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(
                        getWindow(),
                        getWindow().getDecorView()
                );
        if (controller != null) {
            controller.setAppearanceLightNavigationBars(true);
        }

        setContentView(R.layout.activity_menu_selecione);

        // 1. Vinculação dos componentes
        rvEventos        = findViewById(R.id.rv_eventos);
        cardLista        = findViewById(R.id.card_lista_eventos);
        containerFiltros = findViewById(R.id.container_filtros_extras);
        editSearch       = findViewById(R.id.edit_search);
        editDataFiltro   = findViewById(R.id.edit_data_filtro);
        rgStatus         = findViewById(R.id.rg_status);
        rbAndamento      = findViewById(R.id.rb_andamento);
        rbOferta         = findViewById(R.id.rb_oferta);
        spinnerFundacoes = findViewById(R.id.spinner_fundacoes);
        Button btnFiltros        = findViewById(R.id.btn_filtros);

        // 2. RecyclerView
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventoAdapter(listaFiltrada);
        rvEventos.setAdapter(adapter);

        // 3. Spinner de fundações
        // Procure por essa linha no seu MenuSelecione.java e atualize:
        String[] fundacoes = {
                "Selecione a fundação",
                "Cientec",
                "Funarbe",
                "Facev"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, fundacoes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFundacoes.setAdapter(spinnerAdapter);

        spinnerFundacoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    // Carrega os dados se houver qualquer instituição válida selecionada
                    carregarDados();
                } else {
                    cardLista.setVisibility(View.GONE);
                    listaCompleta.clear();
                    listaFiltrada.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 4. Botão de filtros (expande/recolhe painel)
        btnFiltros.setOnClickListener(v -> {
            boolean visivel = containerFiltros.getVisibility() == View.VISIBLE;
            containerFiltros.setVisibility(visivel ? View.GONE : View.VISIBLE);
        });

        // 5. Escutadores para filtro em tempo real (Texto e Data)
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { aplicarFiltros(); }
            @Override public void afterTextChanged(Editable s) {}
        };
        editSearch.addTextChangedListener(watcher);
        editDataFiltro.addTextChangedListener(watcher);

        // 6. Permitir desclicar nos RadioButtons de Status
        View.OnClickListener radioClickListener = new View.OnClickListener() {
            private int ultimoIdMarcado = -1;

            @Override
            public void onClick(View v) {
                int idClicado = v.getId();
                if (idClicado == ultimoIdMarcado) {
                    rgStatus.clearCheck();
                    ultimoIdMarcado = -1;
                } else {
                    ultimoIdMarcado = idClicado;
                }
                aplicarFiltros();
            }
        };

        if (rbAndamento != null && rbOferta != null) {
            rbAndamento.setOnClickListener(radioClickListener);
            rbOferta.setOnClickListener(radioClickListener);
        }
    }

    private void carregarDados() {
        listaCompleta.clear();
        listaCompleta.addAll(MockRepository.getEventos(this));
        aplicarFiltros();
        cardLista.setVisibility(View.VISIBLE);
    }

    private void aplicarFiltros() {
        String busca = editSearch.getText().toString().trim().toLowerCase(Locale.getDefault());
        String dataBuscaStr = editDataFiltro.getText().toString().trim();
        int selectedId = rgStatus.getCheckedRadioButtonId();

        // Identifica qual instituição está ativa no Spinner
        String fundacaoSelecionada = spinnerFundacoes.getSelectedItem().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dataLimite = null;

        if (dataBuscaStr.length() == 10) {
            try {
                dataLimite = sdf.parse(dataBuscaStr);
            } catch (ParseException ignored) { }
        }

        listaFiltrada.clear();

        for (Evento e : listaCompleta) {

            // NOVO FILTRO: Restringe os itens à instituição atualmente selecionada
            if (!e.getFundacao().equalsIgnoreCase(fundacaoSelecionada)) continue;

            // Filtro por nome
            if (!e.getCurso().toLowerCase(Locale.getDefault()).contains(busca)) continue;

            // Filtro por status
            if (selectedId == R.id.rb_oferta && !e.getSituacao().equalsIgnoreCase("Em oferta")) continue;
            if (selectedId == R.id.rb_andamento && !e.getSituacao().equalsIgnoreCase("Em andamento")) continue;

            // Filtro por data
            if (dataLimite != null) {
                try {
                    Date dataEvento = sdf.parse(e.getDataInicio());
                    if (dataEvento == null || dataEvento.before(dataLimite)) continue;
                } catch (ParseException ignored) { continue; }
            }

            listaFiltrada.add(e);
        }

        adapter.notifyDataSetChanged();
    }
}