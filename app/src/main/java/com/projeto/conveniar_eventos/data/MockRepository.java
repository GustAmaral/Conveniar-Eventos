package com.projeto.conveniar_eventos.data;

import android.content.Context;

import com.projeto.conveniar_eventos.models.Evento;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositório de dados mock com informações ricas e variadas por evento.
 *
 * TROCA FUTURA: substitua este repositório por ApiRepository quando a API
 * estiver disponível. O restante do app (MenuSelecione, DetalhesEvento etc.)
 * não precisa mudar, pois todos consomem List<Evento>.
 */
public class MockRepository {

    private static List<Evento> cache = null;

    /**
     * Retorna a lista de eventos e inicializa o controle de vagas no banco,
     * garantindo que reinicializações do app não "resetem" as vagas.
     */
    public static List<Evento> getEventos(Context ctx) {
        if (cache != null) return cache;

        DatabaseHelper db = DatabaseHelper.getInstance(ctx);
        List<Evento> lista = new ArrayList<>();

        // ── 1 ── GESTÃO
        lista.add(new Evento(
                1,
                "Gestão de Pessoas e Liderança",
                "Em oferta",
                40,
                "10/07/2025", "11/07/2025",
                "08h00 às 17h00",
                "Sede Cientec – Av. Álvares Cabral, 1600, BH",
                490.00, 16,
                "Dra. Mariana Souza (Especialista em RH)",
                "Desenvolva habilidades de liderança situacional, gestão por competências e técnicas de feedback efetivo. Indicado para gestores, coordenadores e líderes em formação.",
                Evento.TIPO_GESTAO,
                ""
        ));

        // ── 2 ── TI
        lista.add(new Evento(
                2,
                "Excel Avançado para Gestores",
                "Em oferta",
                30,
                "15/07/2025", "16/07/2025",
                "13h00 às 18h00",
                "Online (Microsoft Teams)",
                350.00, 10,
                "Prof. Carlos Menezes (Analista de Dados FEPE)",
                "Power Query, tabelas dinâmicas avançadas, macros VBA básicas e dashboards executivos. Pré-requisito: Excel intermediário.",
                Evento.TIPO_TI,
                ""
        ));

        // ── 3 ── JURÍDICO (em andamento, sem vagas)
        lista.add(new Evento(
                3,
                "Licitações e Contratos Administrativos",
                "Em andamento",
                0,
                "02/06/2025", "04/06/2025",
                "08h30 às 12h30",
                "Online (Zoom)",
                580.00, 12,
                "Dr. Rafael Borges (OAB/MG 45.321)",
                "Atualização completa sobre a Nova Lei de Licitações (Lei 14.133/2021): modalidades, prazos, sanções e jurisprudência do TCU.",
                Evento.TIPO_JURIDICO,
                ""
        ));

        // ── 4 ── GESTÃO
        lista.add(new Evento(
                4,
                "Compliance e Governança Corporativa",
                "Em oferta",
                25,
                "22/07/2025", "23/07/2025",
                "09h00 às 16h00",
                "Sede Cientec – BH",
                620.00, 14,
                "Dra. Fernanda Leal (Consultora de Compliance)",
                "Estruturação de programas de integridade, análise de risco de corrupção, canais de denúncia e LGPD aplicada ao setor público.",
                Evento.TIPO_GESTAO,
                ""
        ));

        // ── 5 ── COMUNICAÇÃO (em andamento)
        lista.add(new Evento(
                5,
                "Comunicação Assertiva no Ambiente de Trabalho",
                "Em andamento",
                0,
                "10/05/2025", "10/05/2025",
                "09h00 às 13h00",
                "Online (Google Meet)",
                280.00, 4,
                "Prof.ª Ana Clara Ramos (Coach Executiva)",
                "Técnicas de comunicação não-violenta, escuta ativa e gestão de conversas difíceis. Dinâmicas em grupo incluídas.",
                Evento.TIPO_COMUNICACAO,
                ""
        ));

        // ── 6 ── TI
        lista.add(new Evento(
                6,
                "Introdução à Análise de Dados com Power BI",
                "Em oferta",
                35,
                "05/08/2025", "07/08/2025",
                "18h30 às 21h30",
                "Online (Microsoft Teams)",
                420.00, 9,
                "Eng. Lucas Ferreira (MVP Power BI)",
                "Do zero ao dashboard: conexão com fontes de dados, modelagem dimensional, DAX básico e publicação de relatórios no Power BI Service.",
                Evento.TIPO_TI,
                ""
        ));

        // ── 7 ── GESTÃO
        lista.add(new Evento(
                7,
                "Gestão Financeira para Não Financeiros",
                "Em oferta",
                20,
                "18/08/2025", "19/08/2025",
                "08h00 às 17h00",
                "Sede Cientec – BH",
                530.00, 16,
                "Prof. Rodrigo Andrade (MBA FGV Finanças)",
                "Leitura de balanços, análise de fluxo de caixa, indicadores DRE e tomada de decisão com base em dados financeiros. Sem necessidade de conhecimento prévio em contabilidade.",
                Evento.TIPO_GESTAO,
                ""
        ));

        // ── 8 ── COMUNICAÇÃO (em andamento)
        lista.add(new Evento(
                8,
                "Redação Oficial e Documentação Técnica",
                "Em andamento",
                0,
                "12/05/2025", "14/05/2025",
                "14h00 às 17h00",
                "Online (Zoom)",
                310.00, 9,
                "Prof.ª Carla Dutra (Revisora Oficial – MG)",
                "Elaboração de ofícios, memorandos, relatórios técnicos e atas conforme o Manual de Redação da Presidência da República (4ª ed.).",
                Evento.TIPO_COMUNICACAO,
                ""
        ));

        // ── 9 ── GESTÃO
        lista.add(new Evento(
                9,
                "Planejamento Estratégico e OKRs",
                "Em oferta",
                50,
                "01/09/2025", "02/09/2025",
                "09h00 às 18h00",
                "Sede Cientec – BH",
                460.00, 16,
                "Dr. Thiago Vasconcelos (Consultor McKinsey – ex)",
                "Metodologia OKR aplicada a órgãos públicos e empresas: definição de objetivos, Key Results, rituais de acompanhamento e alinhamento estratégico.",
                Evento.TIPO_GESTAO,
                ""
        ));

        // ── 10 ── TI
        lista.add(new Evento(
                10,
                "Segurança da Informação — Fundamentos",
                "Em oferta",
                30,
                "10/09/2025", "11/09/2025",
                "08h00 às 17h00",
                "Online (Microsoft Teams)",
                490.00, 16,
                "Esp. Bruno Carvalho (CISM, CISSP)",
                "Fundamentos de cibersegurança, gestão de riscos, LGPD e GDPR, políticas de senhas, engenharia social e resposta a incidentes. Preparatório para certificações CompTIA.",
                Evento.TIPO_TI,
                ""
        ));

        // ── 11 ── JURÍDICO (em andamento)
        lista.add(new Evento(
                11,
                "Mediação e Resolução de Conflitos",
                "Em andamento",
                0,
                "20/05/2025", "21/05/2025",
                "09h00 às 13h00",
                "Sede Cientec – BH",
                370.00, 8,
                "Dra. Patrícia Nunes (Mediadora Certificada CNJ)",
                "Técnicas de mediação e conciliação, ética do mediador, sessões conjuntas e individuais. Válido para pontuação na OAB e certificação CNJ.",
                Evento.TIPO_JURIDICO,
                ""
        ));

        // ── 12 ── GESTÃO
        lista.add(new Evento(
                12,
                "Gestão de Projetos com Metodologia Ágil",
                "Em oferta",
                45,
                "15/09/2025", "17/09/2025",
                "18h00 às 22h00",
                "Online (Google Meet)",
                400.00, 12,
                "Prof. Eduardo Lima (PMP, PSM I)",
                "Scrum, Kanban e híbridos ágeis aplicados a projetos reais. Simulado de certificação PMI-ACP incluso. Indicado para gestores de projetos e equipes de produto.",
                Evento.TIPO_GESTAO,
                ""
        ));

        // ── Inicializa vagas no banco (só na primeira execução) ──────
        for (Evento e : lista) {
            db.inicializarVagasSeNecessario(e.getId(), e.getVagas());
        }

        // ── Sincroniza vagas do banco com os objetos em memória ──────
        // (garante consistência após inscrições feitas em sessões anteriores)
        for (Evento e : lista) {
            // Se evento "Em andamento" mantemos vagas = 0 (já está no banco como 0)
            if (!e.getSituacao().equalsIgnoreCase("Em andamento")) {
                int vagasBanco = db.getVagasDisponiveis(e.getId());
                // Usa reflexão é indesejável; em vez disso, Evento expõe setVagas
                // (adicionado na versão 2 do modelo — veja setter abaixo)
                e.setVagas(vagasBanco);
            }
        }

        cache = lista;
        return lista;
    }

    /** Limpa o cache em memória (útil após inscrição bem-sucedida). */
    public static void invalidarCache() {
        cache = null;
    }
}