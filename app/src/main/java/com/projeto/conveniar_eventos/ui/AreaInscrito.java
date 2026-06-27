package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.adapters.InscricaoAdapter;
import com.projeto.conveniar_eventos.data.DatabaseHelper;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;
import com.projeto.conveniar_eventos.worker.DocumentUploadWorker;

import java.util.ArrayList;
import java.util.List;

public class AreaInscrito extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_inscrito);

        configurarToolbar("Meus Eventos", false);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        configurarBottomNav(bottomNav, R.id.nav_inscrito);

        TextView tvBoasVindas = findViewById(R.id.tv_boas_vindas);
        RecyclerView rvInscricoes = findViewById(R.id.rv_inscricoes);
        TextView tvVazio = findViewById(R.id.tv_lista_vazia);

        String nome   = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getString("usuario_nome", "Usuário");
        long   userId = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getLong("usuario_id", -1);

        tvBoasVindas.setText("Painel de: " + nome);

        List<InscricaoAdapter.ItemInscricao> items = carregarInscricoes(userId);

        if (items.isEmpty()) {
            tvVazio.setVisibility(View.VISIBLE);
            rvInscricoes.setVisibility(View.GONE);
        } else {
            tvVazio.setVisibility(View.GONE);
            rvInscricoes.setVisibility(View.VISIBLE);
            rvInscricoes.setLayoutManager(new LinearLayoutManager(this));
            rvInscricoes.setAdapter(new InscricaoAdapter(items, eventoId -> {
                Intent it = new Intent(this, DetalhesEvento.class);
                it.putExtra("EVENTO_ID", eventoId);
                startActivity(it);
            }));
        }
    }

    // ── Menu do toolbar (ícone de logout) ──────────────────────────────

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_area_inscrito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            realizarLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<InscricaoAdapter.ItemInscricao> carregarInscricoes(long userId) {
        List<InscricaoAdapter.ItemInscricao> items = new ArrayList<>();
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<Evento> todosEventos = MockRepository.getEventos(this);

        Cursor c = db.getInscricoesUsuario(userId);
        if (c == null) return items;

        while (c.moveToNext()) {
            int    evId = c.getInt(0);
            String data = c.getString(1);

            String nomeCurso = "Curso desconhecido (ID: " + evId + ")";
            for (Evento e : todosEventos) {
                if (e.getId() == evId) { nomeCurso = e.getCurso(); break; }
            }

            long inscricaoId   = db.getInscricaoId(userId, evId);
            int  total         = db.contarDocumentosDaInscricao(inscricaoId);
            int  enviados      = db.contarDocumentosPorStatus(inscricaoId, DatabaseHelper.DOC_STATUS_ENVIADO);
            int  rejeitados    = db.contarDocumentosPorStatus(inscricaoId, DatabaseHelper.DOC_STATUS_REJEITADO);

            items.add(new InscricaoAdapter.ItemInscricao(evId, nomeCurso, data, total, enviados, rejeitados));
        }
        c.close();
        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();
        reenviarRejeitados();
    }

    /**
     * Ao voltar para a tela, re-agenda o worker para quaisquer inscrições que
     * ainda tenham documentos rejeitados, permitindo reenvio sem refazer a inscrição.
     */
    private void reenviarRejeitados() {
        long userId = getSharedPreferences("conveniar_prefs", MODE_PRIVATE).getLong("usuario_id", -1);
        if (userId < 0) return;

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Cursor c = db.getInscricoesUsuario(userId);
        if (c == null) return;

        while (c.moveToNext()) {
            int  evId        = c.getInt(0);
            long inscricaoId = db.getInscricaoId(userId, evId);
            int  rejeitados  = db.contarDocumentosPorStatus(inscricaoId, DatabaseHelper.DOC_STATUS_REJEITADO);

            if (rejeitados > 0) {
                // Marca os rejeitados como pendentes antes de re-agendar
                remarcarRejeitadosComoPendentes(db, inscricaoId);
                agendarReenvio(inscricaoId);
            }
        }
        c.close();
    }

    private void remarcarRejeitadosComoPendentes(DatabaseHelper db, long inscricaoId) {
        try (Cursor docCursor = db.getDocumentosDaInscricao(inscricaoId)) {
            int colId     = docCursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOC_ID);
            int colStatus = docCursor.getColumnIndexOrThrow(DatabaseHelper.COL_DOC_STATUS);
            while (docCursor.moveToNext()) {
                if (DatabaseHelper.DOC_STATUS_REJEITADO.equals(docCursor.getString(colStatus))) {
                    db.atualizarStatusDocumento(docCursor.getLong(colId), DatabaseHelper.DOC_STATUS_PENDENTE);
                }
            }
        }
    }

    private void agendarReenvio(long inscricaoId) {
        Data inputData = new Data.Builder()
                .putLong(DocumentUploadWorker.KEY_INSCRICAO_ID, inscricaoId)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DocumentUploadWorker.class)
                .setInputData(inputData)
                .addTag("reenvio_documentos_" + inscricaoId)
                .build();

        WorkManager.getInstance(this).enqueue(request);
    }

}