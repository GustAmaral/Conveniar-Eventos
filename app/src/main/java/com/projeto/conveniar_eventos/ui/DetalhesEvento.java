package com.projeto.conveniar_eventos.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.projeto.conveniar_eventos.R;

public class DetalhesEvento extends AppCompatActivity {

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

        // 1. Vincular os componentes do layout XML
        TextView tvNome = findViewById(R.id.tv_valor_nome);
        TextView tvVagas = findViewById(R.id.tv_valor_vagas);
        WebView webView = findViewById(R.id.web_informacoes);

        // 2. Recuperar os dados enviados pelo EventoAdapter
        String nomeEvento = getIntent().getStringExtra("NOME_CURSO");
        String dataInicio = getIntent().getStringExtra("DATA_INICIO");
        int vagas = getIntent().getIntExtra("VAGAS", 0);

        // 3. Exibir os dados básicos nos TextViews
        if (nomeEvento != null) {
            tvNome.setText(nomeEvento);
        }

        // Exibe a data e as vagas (ou aviso de edital)
        String infoVagas = (dataInicio != null ? "Início: " + dataInicio + " | " : "") +
                "Vagas: " + (vagas > 0 ? vagas : "Consulte o edital");
        tvVagas.setText(infoVagas);

        // 4. Restaurar o conteúdo estático do WebView
        if (webView != null) {
            String htmlDocumento = "<html><head>" +
                    "<style>" +
                    "body { font-family: sans-serif; padding: 15px; line-height: 1.5; color: #444; background-color: #ffffff; }" +
                    "h2 { color: #0067AB; font-size: 18px; border-bottom: 1px solid #eee; padding-bottom: 8px; }" +
                    "p { font-size: 14px; }" +
                    "ul { padding-left: 20px; font-size: 14px; }" +
                    "li { margin-bottom: 8px; }" +
                    ".highlight { color: #0067AB; font-weight: bold; }" +
                    "</style>" +
                    "</head><body>" +
                    "<h2>Informações Gerais</h2>" +
                    "<p>Detalhes sobre o curso <span class='highlight'>" + (nomeEvento != null ? nomeEvento : "selecionado") + "</span>.</p>" +
                    "<p><b>Regras de Inscrição:</b></p>" +
                    "<ul>" +
                    "<li>A inscrição deve ser confirmada após o pagamento do boleto.</li>" +
                    "<li>O certificado será emitido para alunos com 75% de presença.</li>" +
                    "<li>O material didático será enviado por e-mail 24h antes do início.</li>" +
                    "</ul>" +
                    "<p>Para mais informações, acesse o portal da fundação ou entre em contato com o suporte.</p>" +
                    "</body></html>";

            webView.loadDataWithBaseURL(null, htmlDocumento, "text/html", "UTF-8", null);
        }
    }
}