package com.projeto.conveniar_eventos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.DatabaseHelper;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;
import com.projeto.conveniar_eventos.util.DocumentPickerHelper;
import com.projeto.conveniar_eventos.worker.DocumentUploadWorker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InscricaoEvento extends BaseActivity {

    private Evento evento;
    private long userId;

    private TextView tvTituloInscricao;
    private EditText etMatricula, etLinkedin, etAreaAtuacao, etOab, etMotivacao;
    private Spinner spNivelTi;
    private LinearLayout llGestaoJuridico, llTi, llComunicacaoGestao, llOab;
    private LinearLayout llSecaoDocumentos, llContainerDocumentos;
    private Button btnConfirmar;

    private DocumentPickerHelper documentPickerHelper;

    // tipo → caminho local do arquivo selecionado
    private final Map<String, String> documentosSelecionados = new HashMap<>();
    // tipo → nome de exibição
    private final Map<String, String> documentosNomes = new HashMap<>();

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

        configurarToolbar("Inscrição", true);
        vincularViews();

        // DocumentPickerHelper precisa ser criado antes de qualquer interação com o usuário
        documentPickerHelper = new DocumentPickerHelper(this, new DocumentPickerHelper.Callback() {
            @Override
            public void onDocumentSelecionado(String tipo, String nomeExibicao, String caminhoLocal) {
                documentosSelecionados.put(tipo, caminhoLocal);
                documentosNomes.put(tipo, nomeExibicao);
                atualizarCardDocumento(tipo, nomeExibicao);
                atualizarEstadoBotaoConfirmar();
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(InscricaoEvento.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });

        tvTituloInscricao.setText("Inscrição:\n" + evento.getCurso());
        configurarFormularioDinamico();
        inflarDocumentosRequeridos();
        atualizarEstadoBotaoConfirmar();

        btnConfirmar.setOnClickListener(v -> processarInscricao());
    }

    private void vincularViews() {
        tvTituloInscricao       = findViewById(R.id.tv_titulo_inscricao);
        etMatricula             = findViewById(R.id.et_ins_matricula);
        etLinkedin              = findViewById(R.id.et_ins_linkedin);
        etAreaAtuacao           = findViewById(R.id.et_ins_area_atuacao);
        etOab                   = findViewById(R.id.et_ins_oab);
        etMotivacao             = findViewById(R.id.et_ins_motivacao);
        spNivelTi               = findViewById(R.id.sp_ins_nivel_ti);
        llGestaoJuridico        = findViewById(R.id.ll_campos_gestao_juridico);
        llTi                    = findViewById(R.id.ll_campos_ti);
        llComunicacaoGestao     = findViewById(R.id.ll_campos_comunicacao_gestao);
        llOab                   = findViewById(R.id.ll_campos_oab);
        llSecaoDocumentos       = findViewById(R.id.ll_secao_documentos);
        llContainerDocumentos   = findViewById(R.id.ll_container_documentos);
        btnConfirmar            = findViewById(R.id.btn_confirmar_inscricao);
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

    // ── Documentos ───────────────────────────────────────────────────

    private void inflarDocumentosRequeridos() {
        List<String> docs = evento.getDocumentosRequeridos();
        if (docs.isEmpty()) return;

        llSecaoDocumentos.setVisibility(View.VISIBLE);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (String tipo : docs) {
            View card = inflater.inflate(R.layout.item_documento_upload, llContainerDocumentos, false);
            card.setTag(tipo);

            TextView tvLabel  = card.findViewById(R.id.tv_doc_label);
            Button btnSelecionar = card.findViewById(R.id.btn_selecionar_arquivo);

            tvLabel.setText(labelParaTipo(tipo));
            btnSelecionar.setOnClickListener(v -> documentPickerHelper.open(tipo));

            llContainerDocumentos.addView(card);
        }
    }

    private void atualizarCardDocumento(String tipo, String nomeExibicao) {
        View card = llContainerDocumentos.findViewWithTag(tipo);
        if (card == null) return;

        TextView tvNome   = card.findViewById(R.id.tv_doc_nome_arquivo);
        TextView tvStatus = card.findViewById(R.id.tv_doc_status);

        tvNome.setText(nomeExibicao);
        tvStatus.setText("Selecionado");
        tvStatus.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));
    }

    private void atualizarEstadoBotaoConfirmar() {
        List<String> docsRequeridos = evento.getDocumentosRequeridos();
        boolean todosAnexados = true;
        for (String tipo : docsRequeridos) {
            if (!documentosSelecionados.containsKey(tipo)) {
                todosAnexados = false;
                break;
            }
        }
        btnConfirmar.setEnabled(todosAnexados);
        btnConfirmar.setAlpha(todosAnexados ? 1f : 0.5f);
    }

    private String labelParaTipo(String tipo) {
        switch (tipo) {
            case Evento.DOC_CPF:                  return getString(R.string.doc_label_cpf);
            case Evento.DOC_DIPLOMA:              return getString(R.string.doc_label_diploma);
            case Evento.DOC_OAB:                  return getString(R.string.doc_label_oab);
            case Evento.DOC_COMPROVANTE_VINCULO:  return getString(R.string.doc_label_comprovante_vinculo);
            case Evento.DOC_CURRICULO:            return getString(R.string.doc_label_curriculo);
            default:                              return getString(R.string.doc_label_desconhecido);
        }
    }

    // ── Inscrição ────────────────────────────────────────────────────

    private void processarInscricao() {
        String matricula  = etMatricula.getText().toString().trim();
        String linkedin   = etLinkedin.getText().toString().trim();
        String area       = etAreaAtuacao.getText().toString().trim();
        String oab        = etOab.getText().toString().trim();
        String motivacao  = etMotivacao.getText().toString().trim();
        String nivelTi    = spNivelTi.getSelectedItem().toString();

        if (evento.getTipoEvento().equals(Evento.TIPO_JURIDICO) && oab.isEmpty()) {
            Toast.makeText(this, "Número da OAB é obrigatório para cursos jurídicos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String dataHoje = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        boolean sucesso = db.inscrever(userId, evento.getId(), dataHoje, matricula,
                evento.getTipoEvento().equals(Evento.TIPO_TI) ? nivelTi : "",
                linkedin, area, oab, motivacao);

        if (!sucesso) {
            Toast.makeText(this, "Erro ao processar inscrição. Verifique as vagas.", Toast.LENGTH_SHORT).show();
            return;
        }

        salvarDocumentosLocais(db);

        MockRepository.invalidarCache();
        Toast.makeText(this, "Inscrição realizada com sucesso!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void salvarDocumentosLocais(DatabaseHelper db) {
        long inscricaoId = db.getInscricaoId(userId, evento.getId());
        if (inscricaoId < 0) return;

        for (Map.Entry<String, String> entry : documentosSelecionados.entrySet()) {
            String tipo         = entry.getKey();
            String caminho      = entry.getValue();
            String nomeExibicao = documentosNomes.getOrDefault(tipo, "documento");
            db.salvarDocumento(inscricaoId, tipo, nomeExibicao, caminho);
        }

        agendarUpload(inscricaoId);
    }

    private void agendarUpload(long inscricaoId) {
        Data inputData = new Data.Builder()
                .putLong(DocumentUploadWorker.KEY_INSCRICAO_ID, inscricaoId)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DocumentUploadWorker.class)
                .setInputData(inputData)
                .addTag("upload_documentos_" + inscricaoId)
                .build();

        WorkManager.getInstance(this).enqueue(request);
    }
}