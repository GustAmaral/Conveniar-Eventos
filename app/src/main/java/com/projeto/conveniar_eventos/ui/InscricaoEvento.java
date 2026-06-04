package com.projeto.conveniar_eventos.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.DatabaseHelper;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InscricaoEvento extends AppCompatActivity {

    private Evento evento;
    private long userId;

    private TextView tvTituloInscricao;
    private EditText etMatricula, etLinkedin, etAreaAtuacao, etOab, etMotivacao;
    private Spinner spNivelTi;
    private LinearLayout llGestaoJuridico, llTi, llComunicacaoGestao, llOab;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao_evento);

        userId = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getLong("usuario_id", -1);
        int eventoId = getIntent().getIntExtra("EVENTO_ID", -1);

        for (Evento e : MockRepository.getEventos(this)) {
            if (e.getId() == eventoId) { evento = e; break; }
        }

        if (evento == null || userId == -1) {
            Toast.makeText(this, "Erro ao carregar dados do formulário.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Vinculação
        tvTituloInscricao   = findViewById(R.id.tv_titulo_inscricao);
        etMatricula         = findViewById(R.id.et_ins_matricula);
        etLinkedin          = findViewById(R.id.et_ins_linkedin);
        etAreaAtuacao       = findViewById(R.id.et_ins_area_atuacao);
        etOab               = findViewById(R.id.et_ins_oab);
        etMotivacao         = findViewById(R.id.et_ins_motivacao);
        spNivelTi           = findViewById(R.id.sp_ins_nivel_ti);

        llGestaoJuridico    = findViewById(R.id.ll_campos_gestao_juridico);
        llTi                = findViewById(R.id.ll_campos_ti);
        llComunicacaoGestao = findViewById(R.id.ll_campos_comunicacao_gestao);
        llOab               = findViewById(R.id.ll_campos_oab);
        btnConfirmar        = findViewById(R.id.btn_confirmar_inscricao);

        tvTituloInscricao.setText("Inscrição:\n" + evento.getCurso());
        configurarFormularioDinamico();

        btnConfirmar.setOnClickListener(v -> processarInscricao());
    }

    private void configurarFormularioDinamico() {
        String tipo = evento.getTipoEvento();

        if (tipo.equals(Evento.TIPO_GESTAO)) {
            llGestaoJuridico.setVisibility(View.VISIBLE);
            llComunicacaoGestao.setVisibility(View.VISIBLE);
        } else if (tipo.equals(Evento.TIPO_JURIDICO)) {
            llGestaoJuridico.setVisibility(View.VISIBLE);
            llOab.setVisibility(View.VISIBLE);
        } else if (tipo.equals(Evento.TIPO_TI)) {
            llTi.setVisibility(View.VISIBLE);
        } else if (tipo.equals(Evento.TIPO_COMUNICACAO)) {
            llComunicacaoGestao.setVisibility(View.VISIBLE);
        }
    }

    private void processarInscricao() {
        String matricula = etMatricula.getText().toString().trim();
        String linkedin = etLinkedin.getText().toString().trim();
        String area = etAreaAtuacao.getText().toString().trim();
        String oab = etOab.getText().toString().trim();
        String motivacao = etMotivacao.getText().toString().trim();
        String nivelTi = spNivelTi.getSelectedItem().toString();

        // Validações básicas por regras dinâmicas
        if (evento.getTipoEvento().equals(Evento.TIPO_JURIDICO) && oab.isEmpty()) {
            Toast.makeText(this, "Número da OAB é obrigatório para cursos jurídicos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String dataHoje = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        boolean sucesso = db.inscrever(userId, evento.getId(), dataHoje, matricula,
                evento.getTipoEvento().equals(Evento.TIPO_TI) ? nivelTi : "",
                linkedin, area, oab, motivacao);

        if (sucesso) {
            MockRepository.invalidarCache(); // Força atualização das vagas em memória
            Toast.makeText(this, "Inscrição realizada com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao processar inscrição. Verifique as vagas.", Toast.LENGTH_SHORT).show();
        }
    }
}