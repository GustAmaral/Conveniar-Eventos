package com.projeto.conveniar_eventos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.projeto.conveniar_eventos.R;
import com.projeto.conveniar_eventos.data.MockRepository;
import com.projeto.conveniar_eventos.models.Evento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermosAceiteEvento extends BaseActivity {

    private Evento evento;
    private CheckBox cbAceite;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_aceite);

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

        configurarToolbar(getString(R.string.termos_toolbar_titulo), true);

        WebView webView   = findViewById(R.id.web_termos);
        cbAceite          = findViewById(R.id.cb_aceite_termos);
        btnContinuar      = findViewById(R.id.btn_continuar_inscricao);

        cbAceite.setEnabled(false);
        btnContinuar.setEnabled(false);
        btnContinuar.setAlpha(0.5f);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new ScrollInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Habilita o checkbox ao atingir o fim — ou imediatamente se não houver scroll
                view.evaluateJavascript(
                    "(function() {" +
                    "  function verificarFim() {" +
                    "    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight - 20) {" +
                    "      Android.onScrolledToBottom();" +
                    "    }" +
                    "  }" +
                    "  window.addEventListener('scroll', verificarFim);" +
                    "  verificarFim();" +
                    "})();",
                    null
                );
            }
        });
        webView.loadDataWithBaseURL(null, montarHtmlTermos(), "text/html", "UTF-8", null);

        cbAceite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnContinuar.setEnabled(isChecked);
            btnContinuar.setAlpha(isChecked ? 1f : 0.5f);
        });

        btnContinuar.setOnClickListener(v -> {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    .format(new Date());
            Intent it = new Intent(this, InscricaoEvento.class);
            it.putExtra("EVENTO_ID", evento.getId());
            it.putExtra("ACEITE_TIMESTAMP", timestamp);
            startActivity(it);
            finish();
        });
    }

    private class ScrollInterface {
        @JavascriptInterface
        public void onScrolledToBottom() {
            runOnUiThread(() -> {
                if (!cbAceite.isEnabled()) {
                    cbAceite.setEnabled(true);
                }
            });
        }
    }

    private String montarHtmlTermos() {
        String cor = "#0067AB";
        return "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1'>" +
                "<style>" +
                "body{font-family:sans-serif;padding:14px;line-height:1.6;color:#333;background:#fff;}" +
                "h1{color:" + cor + ";font-size:18px;border-bottom:2px solid #ddd;padding-bottom:8px;margin-top:0;}" +
                "h2{color:" + cor + ";font-size:15px;border-bottom:1px solid #eee;padding-bottom:4px;margin-top:20px;}" +
                "p{font-size:14px;margin-bottom:10px;}" +
                "</style></head><body>" +

                "<h1>Termo de Aceite e Condições de Participação</h1>" +
                "<p>Ao prosseguir com a inscrição, o participante declara ter lido, compreendido e aceitado os termos e condições descritos neste documento, bem como as informações específicas disponibilizadas na página do evento ou curso.</p>" +

                "<h2>1. Inscrição</h2>" +
                "<p>A inscrição somente será considerada válida após o preenchimento das informações solicitadas e, quando aplicável, a confirmação do pagamento ou o atendimento aos critérios estabelecidos pela organização do evento ou curso.</p>" +
                "<p>Cada evento ou curso poderá possuir regras específicas relacionadas a prazos, vagas, documentação necessária, critérios de participação e demais requisitos, sendo responsabilidade do participante consultá-las antes de concluir sua inscrição.</p>" +

                "<h2>2. Pagamentos e Cancelamentos</h2>" +
                "<p>Quando houver cobrança de taxa de inscrição, os valores, formas de pagamento, prazos, políticas de reembolso e condições para cancelamento serão definidos exclusivamente pela organização do evento ou curso e informados em sua página de apresentação.</p>" +
                "<p>Ao realizar a inscrição, o participante declara estar ciente dessas condições.</p>" +

                "<h2>3. Participação</h2>" +
                "<p>O participante compromete-se a fornecer informações verdadeiras e atualizadas durante o processo de inscrição.</p>" +
                "<p>A organização poderá cancelar ou indeferir inscrições que apresentem informações incorretas, inconsistentes ou que não atendam aos critérios definidos para o evento ou curso.</p>" +

                "<h2>4. Certificados</h2>" +
                "<p>Quando previstos pela organização, os certificados de participação ou conclusão serão emitidos conforme os critérios estabelecidos para cada evento ou curso, podendo incluir requisitos como presença mínima, frequência, confirmação de participação, realização de atividades avaliativas ou outras condições previamente divulgadas.</p>" +

                "<h2>5. Tratamento de Dados</h2>" +
                "<p>Os dados pessoais informados durante a inscrição serão utilizados exclusivamente para fins relacionados à organização, administração e comunicação do evento ou curso, respeitando a legislação vigente de proteção de dados.</p>" +

                "<h2>6. Alterações e Cancelamento</h2>" +
                "<p>A organização poderá, por motivos técnicos, administrativos ou de força maior, alterar datas, horários, formato, local ou cancelar o evento ou curso, comprometendo-se a comunicar os participantes pelos meios de contato informados durante a inscrição.</p>" +

                "<h2>7. Responsabilidades</h2>" +
                "<p>O aplicativo atua como plataforma de gerenciamento de inscrições, sendo a organização de cada evento ou curso responsável pelas informações divulgadas, critérios de participação, programação, emissão de certificados, cobrança de taxas e demais procedimentos relacionados à atividade.</p>" +

                "<h2>8. Aceite</h2>" +
                "<p>Ao selecionar a opção de aceite e concluir sua inscrição, o participante confirma que leu este Termo de Aceite, concorda com suas disposições e reconhece que também está sujeito às regras específicas descritas na página do evento ou curso escolhido.</p>" +

                "</body></html>";
    }
}