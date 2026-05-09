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

        // 1. Vincular os componentes do layout XML
        TextView tvNome = findViewById(R.id.tv_valor_nome);
        TextView tvVagas = findViewById(R.id.tv_valor_vagas);
        WebView webView = findViewById(R.id.web_informacoes);

        // 2. Recuperar os dados enviados pelo EventoAdapter
        // Importante: As strings "NOME_CURSO" e "VAGAS" devem ser iguais às do Adapter
        String nomeEvento = getIntent().getStringExtra("NOME_CURSO");
        int vagas = getIntent().getIntExtra("VAGAS", 0);
        String dataInicio = getIntent().getStringExtra("DATA_INICIO");

        // 3. Exibir os dados nos TextViews (Design do topo)
        if (nomeEvento != null) {
            tvNome.setText(nomeEvento);
        }

        String textoVagas = "Vagas disponíveis: " + vagas;
        if (dataInicio != null) {
            textoVagas += " | Início: " + dataInicio;
        }
        tvVagas.setText(textoVagas);

        // 4. Configurar e carregar o conteúdo no WebView
        // Ativamos o JavaScript caso o HTML precise
        webView.getSettings().setJavaScriptEnabled(true);

        // Criamos um HTML básico para as "Informações Adicionais"
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
                "<h2>Edital e Regras</h2>" +
                "<p>Bem-vindo às informações detalhadas do curso <span class='highlight'>" + nomeEvento + "</span>.</p>" +
                "<p><b>Critérios de Participação:</b></p>" +
                "<ul>" +
                "<li>Inscrição obrigatória via sistema.</li>" +
                "<li>Frequência mínima de 75% para certificado.</li>" +
                "<li>Material didático incluso em formato PDF.</li>" +
                "</ul>" +
                "<p>Para mais dúvidas, entre em contato com a coordenação da fundação selecionada.</p>" +
                "</body></html>";

        // Carrega o HTML no WebView
        webView.loadDataWithBaseURL(null, htmlDocumento, "text/html", "UTF-8", null);
    }
}