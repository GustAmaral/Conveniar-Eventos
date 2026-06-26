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
                "h2{color:" + cor + ";font-size:17px;border-bottom:1px solid #ddd;padding-bottom:6px;margin-top:18px;}" +
                "p,li{font-size:14px;}" +
                "ul{padding-left:18px;}" +
                "li{margin-bottom:6px;}" +
                "</style></head><body>" +
                "<h2>Termos e Condições de Participação</h2>" +
                "<p>Ao se inscrever em <b>" + evento.getCurso() + "</b>, você declara estar ciente e de acordo com as seguintes condições:</p>" +
                "<h2>Regras de Inscrição</h2>" +
                "<ul>" +
                "<li>A inscrição deve ser confirmada mediante pagamento do boleto em até 3 dias úteis.</li>" +
                "<li>O certificado é emitido para participantes com mínimo de 75% de presença.</li>" +
                "<li>O material didático é enviado por e-mail 24h antes do início.</li>" +
                "<li>Cancelamentos solicitados com menos de 48h não geram reembolso.</li>" +
                "</ul>" +
                "<p style='font-size:12px;color:#999;margin-top:24px;'>Para mais informações, acesse o portal da fundação ou entre em contato com o suporte.</p>" +
                "</body></html>";
    }
}