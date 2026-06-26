package com.projeto.conveniar_eventos.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Interceptor OkHttp que devolve respostas JSON estáticas para todos os
 * endpoints de eventos, sem tocar na rede.
 *
 * Ativado via BuildConfig.USE_MOCK_API = true em build.gradle.kts.
 * Para produção, basta setar USE_MOCK_API = false — nenhum código de chamada muda.
 */
public class MockApiInterceptor implements Interceptor {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final int HTTP_OK   = 200;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request  request = chain.request();
        String   path    = request.url().encodedPath();
        String   method  = request.method();
        String   body    = resolveBody(path, method);

        if (body == null) {
            // Endpoint não mockado — deixa passar (nunca ocorre com mock total)
            return chain.proceed(request);
        }

        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(HTTP_OK)
                .message("OK (mock)")
                .body(ResponseBody.create(JSON, body))
                .build();
    }

    // ── Roteamento ───────────────────────────────────────────────────

    private String resolveBody(String path, String method) {
        // Token
        if (path.equals("/token/eventos"))
            return mockToken();

        // Tipos
        if (path.equals("/eventos/tipos"))
            return mockTiposEvento();

        // Usuário
        if (path.equals("/eventos/usuario")) {
            if ("POST".equals(method) || "PUT".equals(method)) return "{}";
            return mockUsuario();
        }

        // Registro de usuários
        if (path.equals("/eventos/registro-usuarios"))
            return mockRegistroUsuarios();

        // Lista de eventos por IDs
        if (path.equals("/eventos/ids"))
            return mockEventos();

        // Lista de inscrições
        if (path.equals("/eventos/inscricoes"))
            return mockInscricoes();

        // Minicurso-serviço de uma inscrição
        if (path.matches("/eventos/inscricao/\\d+/minicurso-servico"))
            return mockMinicursosServicos();

        // Arquivos de inscrição
        if (path.matches("/eventos/inscricao/\\d+/arquivos"))
            return mockArquivosInscricao();

        // Documentos financeiros de inscrição
        if (path.matches("/eventos/inscricao/\\d+/documentos"))
            return mockDocumentosInscricao();

        // Minicurso-serviço (path alternativo)
        if (path.matches("/eventos/minicurso-servico/\\d+"))
            return mockMinicursosServicos();

        // Arquivos de documento financeiro
        if (path.matches("/eventos/documentos/\\d+/arquivos"))
            return mockArquivosDocumento();

        // Arquivos de evento específico
        if (path.matches("/eventos/\\d+/arquivos"))
            return mockArquivosEvento();

        // Lista geral de eventos (deve vir por último para não capturar subrotas)
        if (path.equals("/eventos"))
            return mockEventos();

        return null;
    }

    // ── Payloads mock ─────────────────────────────────────────────────

    private String mockToken() {
        return "{"
             + "\"acessToken\": \"mock-bearer-token-conveniar\","
             + "\"expires\": 7200"
             + "}";
    }

    private String mockTiposEvento() {
        return "["
             + "{\"codEventoTipo\": 1, \"nomeEventoTipo\": \"Gestão\"},"
             + "{\"codEventoTipo\": 2, \"nomeEventoTipo\": \"TI\"},"
             + "{\"codEventoTipo\": 3, \"nomeEventoTipo\": \"Jurídico\"},"
             + "{\"codEventoTipo\": 4, \"nomeEventoTipo\": \"Comunicação\"}"
             + "]";
    }

    private String mockEventos() {
        return "["
             + "{"
             +   "\"codEvento\": 1,"
             +   "\"codEventoTipo\": 1,"
             +   "\"nomeEvento\": \"Gestão de Pessoas e Liderança\","
             +   "\"nomeConvenio\": \"Cientec\","
             +   "\"categoria\": \"Gestão\","
             +   "\"situacao\": \"Em oferta\","
             +   "\"dataInicio\": \"2026-07-10T08:00:00\","
             +   "\"dataFim\": \"2026-07-11T17:00:00\","
             +   "\"numeroVagas\": 40,"
             +   "\"informacoes\": ["
             +     "{\"nomeEventoInformacao\": \"local\",        \"descEventoInformacao\": \"Sede Cientec – Av. Álvares Cabral, 1600, BH\"},"
             +     "{\"nomeEventoInformacao\": \"horario\",      \"descEventoInformacao\": \"08h00 às 17h00\"},"
             +     "{\"nomeEventoInformacao\": \"instrutor\",    \"descEventoInformacao\": \"Dra. Mariana Souza\"},"
             +     "{\"nomeEventoInformacao\": \"cargaHoraria\", \"descEventoInformacao\": \"16\"},"
             +     "{\"nomeEventoInformacao\": \"descricao\",    \"descEventoInformacao\": \"Desenvolva habilidades de leadership situacional.\"}"
             +   "],"
             +   "\"categoriasInscricao\": ["
             +     "{\"codCategoriaInscricao\": 1, \"nomeCategoria\": \"Geral\", \"dataInicio\": \"2026-06-01T00:00:00\", \"dataFim\": \"2026-07-09T23:59:59\", \"valor\": 490.00}"
             +   "],"
             +   "\"servicosMinicursos\": []"
             + "},"
             + "{"
             +   "\"codEvento\": 2,"
             +   "\"codEventoTipo\": 2,"
             +   "\"nomeEvento\": \"Excel Avançado para Gestores\","
             +   "\"nomeConvenio\": \"Cientec\","
             +   "\"categoria\": \"TI\","
             +   "\"situacao\": \"Em oferta\","
             +   "\"dataInicio\": \"2026-07-15T13:00:00\","
             +   "\"dataFim\": \"2026-07-16T18:00:00\","
             +   "\"numeroVagas\": 30,"
             +   "\"informacoes\": ["
             +     "{\"nomeEventoInformacao\": \"local\",        \"descEventoInformacao\": \"Online (Microsoft Teams)\"},"
             +     "{\"nomeEventoInformacao\": \"horario\",      \"descEventoInformacao\": \"13h00 às 18h00\"},"
             +     "{\"nomeEventoInformacao\": \"instrutor\",    \"descEventoInformacao\": \"Prof. Carlos Menezes\"},"
             +     "{\"nomeEventoInformacao\": \"cargaHoraria\", \"descEventoInformacao\": \"10\"},"
             +     "{\"nomeEventoInformacao\": \"descricao\",    \"descEventoInformacao\": \"Power Query e dashboards executivos.\"}"
             +   "],"
             +   "\"categoriasInscricao\": ["
             +     "{\"codCategoriaInscricao\": 2, \"nomeCategoria\": \"Geral\", \"dataInicio\": \"2026-06-01T00:00:00\", \"dataFim\": \"2026-07-14T23:59:59\", \"valor\": 350.00}"
             +   "],"
             +   "\"servicosMinicursos\": []"
             + "}"
             + "]";
    }

    private String mockInscricoes() {
        return "["
             + "{"
             +   "\"codEventoInscricao\": 101,"
             +   "\"codEvento\": 1,"
             +   "\"nomeEvento\": \"Gestão de Pessoas e Liderança\","
             +   "\"nomeCategoriaInscricao\": \"Geral\","
             +   "\"nomeStatus\": \"Confirmada\","
             +   "\"numeroInscricao\": 1001"
             + "}"
             + "]";
    }

    private String mockArquivosEvento() {
        return "["
             + "{"
             +   "\"codArquivoEvento\": 1,"
             +   "\"nomeArquivoEvento\": \"programacao.pdf\","
             +   "\"tituloArquivoEvento\": \"Programação do Evento\","
             +   "\"codArquivoBinario\": 501,"
             +   "\"codEvento\": 1"
             + "}"
             + "]";
    }

    private String mockArquivosInscricao() {
        return "["
             + "{"
             +   "\"codArquivoEventoInscricao\": 201,"
             +   "\"nomeArquivoEventoInscricao\": \"comprovante.pdf\","
             +   "\"tituloArquivoEventoInscricao\": \"Comprovante de Inscrição\","
             +   "\"descArquivoEventoInscricao\": \"Comprovante gerado após confirmação.\","
             +   "\"codArquivoBinario\": 601"
             + "}"
             + "]";
    }

    private String mockDocumentosInscricao() {
        return "["
             + "{"
             +   "\"codDocFinEvento\": 301,"
             +   "\"parcela\": \"1/1\","
             +   "\"codEvento\": 1,"
             +   "\"tipoDocumento\": \"Boleto\","
             +   "\"dataCriacao\": \"2026-06-25T00:00:00\","
             +   "\"dataVencimento\": \"05/07/2026\","
             +   "\"dataPagamento\": null,"
             +   "\"valor\": 490.00,"
             +   "\"valorPago\": 0.00,"
             +   "\"nomeStatus\": \"Pendente\","
             +   "\"pagamento\": {"
             +     "\"nomeTipoPagamento\": \"Boleto Bancário\","
             +     "\"codBoleto\": 401,"
             +     "\"titulo\": \"Boleto referente à inscrição no evento\","
             +     "\"instrucoes\": \"Não receber após o vencimento.\""
             +   "}"
             + "}"
             + "]";
    }

    private String mockArquivosDocumento() {
        return "["
             + "{"
             +   "\"tituloArquivoDocFinEvento\": \"Boleto PDF\","
             +   "\"nomeArquivoDocFinEvento\": \"boleto_301.pdf\","
             +   "\"descArquivoDocFinEvento\": \"Boleto bancário para pagamento.\","
             +   "\"codArquivoBinario\": 701,"
             +   "\"codArquivoDocFinEvento\": 801"
             + "}"
             + "]";
    }

    private String mockMinicursosServicos() {
        return "[]";
    }

    private String mockRegistroUsuarios() {
        return "["
             + "{"
             +   "\"numeroRegistro\": 1,"
             +   "\"nomePessoa\": \"Gustavo Henrique\","
             +   "\"nomePai\": \"João Henrique\","
             +   "\"nomeMae\": \"Maria Henrique\""
             + "}"
             + "]";
    }

    private String mockUsuario() {
        return "{"
             + "\"numRegistro\": 1,"
             + "\"nome\": \"Gustavo Henrique\","
             + "\"email\": \"gustavo@conveniar.com.br\","
             + "\"senha\": null,"
             + "\"cracha\": \"GH-001\","
             + "\"documento\": \"000.000.000-00\","
             + "\"tipoDocumentoPessoa\": \"Pessoa física brasileira\","
             + "\"sexo\": \"M\","
             + "\"telefoneCelular\": \"(31) 99999-0000\","
             + "\"telefoneCasa\": null,"
             + "\"telefoneEmpresa\": null,"
             + "\"cep\": \"30000-000\","
             + "\"endereco\": \"Av. Álvares Cabral, 1600\","
             + "\"bairro\": \"Santo Agostinho\","
             + "\"cidade\": \"Belo Horizonte\","
             + "\"estado\": \"MG\","
             + "\"pais\": \"Brasil\""
             + "}";
    }
}