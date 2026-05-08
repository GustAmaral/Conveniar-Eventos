package com.projeto.conveniar_eventos.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.projeto.conveniar_eventos.R;

public class DetalhesEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_evento);

        TextView tvNome = findViewById(R.id.tv_valor_nome);
        TextView tvVagas = findViewById(R.id.tv_valor_vagas);
        WebView webView = findViewById(R.id.web_informacoes);

        // pega o nome que veio da tela anterior
        String nomeEvento = getIntent().getStringExtra("NOME_EVENTO");
        int vagas = getIntent().getIntExtra("VAGAS_EVENTO", 0);
        tvNome.setText(nomeEvento);
        tvVagas.setText(String.valueOf(vagas));

        // simulação de conteúdo que viria em formato HTML
        String htmlDocumento = "<html><head>" +
                "<style>body { font-family: 'Segoe UI', sans-serif; padding: 15px; line-height: 1.6; color: #333; }" +
                "h2 { color: #0067AB; border-bottom: 1px solid #ccc; }" +
                "ul { padding-left: 20px; }</style>" +
                "</head><body>" +
                "<h2>Edital de Abertura</h2>" +
                "<p>Este evento visa fornecer <b>conhecimento técnico</b> avançado na área selecionada.</p>" +
                "<p><b>Conteúdo Programático:</b></p>" +
                "<ul>" +
                "<li>Introdução aos conceitos básicos</li>" +
                "<li>Estudo de caso prático</li>" +
                "<li>Avaliação final e certificação</li>" +
                "</ul>" +
                "<p><i>Observação: É necessário 75% de presença para emissão do certificado.</i></p>" +
                "</body></html>";

        webView.loadData(htmlDocumento, "text/html; charset=utf-8", "UTF-8");
    }
}