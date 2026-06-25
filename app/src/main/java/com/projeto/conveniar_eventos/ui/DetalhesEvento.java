package com.projeto.conveniar_eventos.ui;

import android.graphics.Color;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebView;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.DatabaseHelper;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;

import java.util.List;

public class DetalhesEvento extends BaseActivity {

    private static final String PREFS = "conveniar_prefs";
    private static final String KEY_USER_ID = "usuario_id";

    private Evento evento;

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

        setContentView(R.layout.activity_detalhes_evento);

        // ── Recupera o evento pelo ID recebido via Intent ─────────────
        int eventoId = getIntent().getIntExtra("EVENTO_ID", -1);
        List<Evento> eventos = MockRepository.getEventos(this);
        for (Evento e : eventos) {
            if (e.getId() == eventoId) { evento = e; break; }
        }

        if (evento == null) {
            Toast.makeText(this, "Evento não encontrado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        configurarToolbar(evento.getCurso(), true);

        // ── Vincula views ─────────────────────────────────────────────
        TextView tvNome      = findViewById(R.id.tv_valor_nome);
        TextView tvMeta      = findViewById(R.id.tv_meta_info);
        TextView tvVagas     = findViewById(R.id.tv_valor_vagas);
        WebView  webView     = findViewById(R.id.web_informacoes);
        Button   btnInscrever = findViewById(R.id.btn_inscrever);

        // ── Preenche dados básicos ────────────────────────────────────
        tvNome.setText(evento.getCurso());

        String meta = evento.getLocal() + "  •  " +
                evento.getDataInicio() +
                (evento.getDataFim().equals(evento.getDataInicio()) ? "" : " a " + evento.getDataFim()) +
                "  •  " + evento.getHorario() +
                "  •  " + evento.getCargaHoraria() + "h";
        tvMeta.setText(meta);

        atualizarVagasView(tvVagas);

        // ── WebView com HTML rico ─────────────────────────────────────
        webView.loadDataWithBaseURL(null, montarHtml(), "text/html", "UTF-8", null);

        // ── Botão inscrever ───────────────────────────────────────────
        boolean emAndamento = evento.getSituacao().equalsIgnoreCase("Em andamento");
        int vagasBanco = DatabaseHelper.getInstance(this).getVagasDisponiveis(evento.getId());

        if (emAndamento || vagasBanco <= 0) {
            btnInscrever.setText(emAndamento ? "Curso em andamento" : "Vagas esgotadas");
            btnInscrever.setEnabled(false);
            btnInscrever.setAlpha(0.5f);
        } else {
            btnInscrever.setOnClickListener(v -> aoClicarInscrever());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza vagas caso o usuário tenha voltado da tela de inscrição
        TextView tvVagas = findViewById(R.id.tv_valor_vagas);
        if (tvVagas != null && evento != null) {
            int vagasAtuais = DatabaseHelper.getInstance(this).getVagasDisponiveis(evento.getId());
            evento.setVagas(vagasAtuais);
            atualizarVagasView(tvVagas);
        }
    }

    private void atualizarVagasView(TextView tvVagas) {
        int vagasBanco = DatabaseHelper.getInstance(this).getVagasDisponiveis(evento.getId());
        String valorStr = evento.getValor() == 0
                ? "Gratuito"
                : String.format("R$ %.2f", evento.getValor());

        if (evento.getSituacao().equalsIgnoreCase("Em andamento")) {
            tvVagas.setText("Status: Em andamento  |  " + valorStr);
        } else {
            tvVagas.setText("Vagas disponíveis: " + vagasBanco + "  |  " + valorStr);
        }
    }

    private void aoClicarInscrever() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        long userId = prefs.getLong(KEY_USER_ID, -1);

        if (userId == -1) {
            // Usuário não logado → vai para login/cadastro
            Toast.makeText(this,
                    "Você precisa estar cadastrado para se inscrever.",
                    Toast.LENGTH_LONG).show();
            Intent it = new Intent(this, CadastroUsuario.class);
            it.putExtra("EVENTO_ID_APOS_LOGIN", evento.getId());
            startActivity(it);
        } else {
            // Já logado → vai direto para formulário de inscrição
            if (DatabaseHelper.getInstance(this).jaInscrito(userId, evento.getId())) {
                Toast.makeText(this, "Você já está inscrito neste evento!", Toast.LENGTH_LONG).show();
                return;
            }
            Intent it = new Intent(this, InscricaoEvento.class);
            it.putExtra("EVENTO_ID", evento.getId());
            startActivity(it);
        }
    }

    private String montarHtml() {
        String cor = "#0067AB";
        String valorStr = evento.getValor() == 0
                ? "<span style='color:green;font-weight:bold;'>Gratuito</span>"
                : String.format("<span style='color:%s;font-weight:bold;'>R$ %.2f</span>", cor, evento.getValor());

        return "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1'>" +
                "<style>" +
                "body{font-family:sans-serif;padding:14px;line-height:1.6;color:#333;background:#fff;}" +
                "h2{color:" + cor + ";font-size:17px;border-bottom:1px solid #ddd;padding-bottom:6px;margin-top:18px;}" +
                "p,li{font-size:14px;}" +
                "ul{padding-left:18px;}" +
                "li{margin-bottom:6px;}" +
                ".badge{display:inline-block;padding:3px 10px;border-radius:12px;font-size:12px;font-weight:bold;}" +
                ".oferta{background:#e3f2fd;color:#0067AB;}" +
                ".andamento{background:#fff3e0;color:#e65100;}" +
                ".tag{background:#f0f0f0;border-radius:8px;padding:2px 8px;font-size:12px;margin-right:4px;}" +
                "</style></head><body>" +

                "<h2>Sobre o curso</h2>" +
                "<p>" + evento.getDescricao() + "</p>" +

                "<h2>Instrutor</h2>" +
                "<p>" + evento.getInstrutor() + "</p>" +

                "<h2>Informações Gerais</h2>" +
                "<ul>" +
                "<li><b>Data:</b> " + evento.getDataInicio() +
                (evento.getDataFim().equals(evento.getDataInicio()) ? "" : " a " + evento.getDataFim()) + "</li>" +
                "<li><b>Horário:</b> " + evento.getHorario() + "</li>" +
                "<li><b>Local:</b> " + evento.getLocal() + "</li>" +
                "<li><b>Carga horária:</b> " + evento.getCargaHoraria() + " horas</li>" +
                "<li><b>Investimento:</b> " + valorStr + "</li>" +
                "<li><b>Status:</b> <span class='badge " +
                (evento.getSituacao().equalsIgnoreCase("Em oferta") ? "oferta" : "andamento") + "'>" +
                evento.getSituacao() + "</span></li>" +
                "</ul>" +

                "<h2>Regras de Inscrição</h2>" +
                "<ul>" +
                "<li>A inscrição deve ser confirmada mediante pagamento do boleto em até 3 dias úteis.</li>" +
                "<li>O certificado é emitido para participantes com mínimo de 75% de presença.</li>" +
                "<li>O material didático é enviado por e-mail 24h antes do início.</li>" +
                "<li>Cancelamentos solicitados com menos de 48h não geram reembolso.</li>" +
                "</ul>" +

                "<p style='font-size:12px;color:#999;margin-top:16px;'>Para mais informações, acesse o portal da fundação ou entre em contato com o suporte.</p>" +
                "</body></html>";
    }
}