package com.projeto.conveniar_eventos.data;

import android.content.Context;
import com.projeto.conveniar_eventos.models.Evento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MockRepository {

    private static List<Evento> cache = null;

    // Delega para Evento.fromApi() quando vier da API.
    // Para o mock local, usa o mesmo mapa centralizado em Evento via helper abaixo.
    private static List<String> docs(String tipoEvento) {
        return Evento.docsPorTipo(tipoEvento);
    }

    public static List<Evento> getEventos(Context ctx) {
        if (cache != null) return cache;

        DatabaseHelper db = DatabaseHelper.getInstance(ctx);
        List<Evento> lista = new ArrayList<>();

        // =====================================================================
        // ── CIENTEC (IDs 1 a 18) ── 18 Eventos (14 Em Oferta / 4 Em Andamento)
        // =====================================================================
        lista.add(new Evento(1, "Gestão de Pessoas e Liderança", "Em oferta", 40, "10/07/2026", "11/07/2026", "08h00 às 17h00", "Sede Cientec – Av. Álvares Cabral, 1600, BH", 490.00, 16, "Dra. Mariana Souza", "Desenvolva habilidades de leadership situacional.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(2, "Excel Avançado para Gestores", "Em oferta", 30, "15/07/2026", "16/07/2026", "13h00 às 18h00", "Online (Microsoft Teams)", 350.00, 10, "Prof. Carlos Menezes", "Power Query e dashboards executivos.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));
        lista.add(new Evento(3, "Licitações e Contratos Administrativos", "Em andamento", 0, "02/06/2026", "04/06/2026", "08h30 às 12h30", "Online (Zoom)", 580.00, 12, "Dr. Rafael Borges", "Atualização completa sobre a Nova Lei de Licitações.", Evento.TIPO_JURIDICO, "Cientec", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(4, "Introdução à Análise de Dados com Power BI", "Em oferta", 35, "05/08/2026", "07/08/2026", "18h30 às 21h30", "Online (Microsoft Teams)", 420.00, 9, "Eng. Lucas Ferreira", "Do zero ao dashboard dimensional.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));
        lista.add(new Evento(5, "Compliance e Governança Corporativa", "Em oferta", 25, "22/07/2026", "23/07/2026", "09h00 às 16h00", "Sede Cientec – BH", 620.00, 14, "Dra. Fernanda Leal", "Estruturação de programas de integridade.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(6, "Segurança da Informação e Cibersegurança", "Em oferta", 20, "12/08/2026", "14/08/2026", "19h00 às 22h00", "Online (Zoom)", 450.00, 12, "Esp. Bruno Carvalho", "Fundamentos de segurança e engenharia social.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));
        lista.add(new Evento(7, "Planejamento Estratégico com OKRs", "Em oferta", 15, "01/09/2026", "02/09/2026", "09h00 às 18h00", "Sede Cientec – BH", 390.00, 16, "Dr. Thiago Vasconcelos", "Definição de metas e Key Results.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(8, "Auditoria e Controle no Setor Público", "Em andamento", 0, "18/05/2026", "20/05/2026", "14h00 às 17h00", "Online (Google Meet)", 520.00, 12, "Prof. Marcos Abreu", "Mecanismos de controle interno e prestação de contas.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(9, "LGPD na Administração Pública", "Em oferta", 50, "15/09/2026", "16/09/2026", "08h30 às 12h30", "Sede Cientec – BH", 290.00, 8, "Dra. Letícia Cadon", "Adequação de processos e direitos dos titulares.", Evento.TIPO_JURIDICO, "Cientec", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(10, "Desenvolvimento Web com Node.js", "Em oferta", 15, "20/08/2026", "24/08/2026", "19h00 às 22h00", "Online (Teams)", 310.00, 15, "Eng. Gabriel Reis", "Construção de APIs RESTful estruturadas.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));
        lista.add(new Evento(11, "Gestão de Riscos Corporativos", "Em oferta", 30, "10/10/2026", "12/10/2026", "09h00 às 13h00", "Online (Microsoft Teams)", 400.00, 12, "Dra. Sandra Pires", "Mapeamento e mitigação de riscos operacionais.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(12, "Linguagem SQL para Análise de Dados", "Em oferta", 25, "05/09/2026", "08/09/2026", "18h30 às 21h30", "Online (Zoom)", 280.00, 12, "Prof. André Costa", "Consultas, joins, agrupamentos e subqueries.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));
        lista.add(new Evento(13, "Processo Administrativo Disciplinar", "Em oferta", 20, "18/11/2026", "20/11/2026", "14h00 às 18h00", "Sede Cientec – BH", 450.00, 12, "Dr. Hugo Viana", "Fases do PAD, ampla defesa e contraditório.", Evento.TIPO_JURIDICO, "Cientec", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(14, "Business Intelligence com Pentaho", "Em andamento", 0, "15/05/2026", "17/05/2026", "09h00 às 12h00", "Online (Google Meet)", 490.00, 9, "Alexandre Mota", "Processos de ETL e integração de dados.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));
        lista.add(new Evento(15, "Formação de Líderes Ágeis", "Em oferta", 25, "05/12/2026", "06/12/2026", "08h00 às 17h00", "Sede Cientec – BH", 590.00, 16, "Dra. Mariana Souza", "Mindset ágil e facilitação de equipas de alta performance.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(16, "Metodologia Scrum na Prática", "Em oferta", 30, "01/10/2026", "03/10/2026", "14h00 às 18h00", "Online (Teams)", 320.00, 12, "Prof. Carlos Menezes", "Papéis, eventos e artefatos do framework Scrum.", Evento.TIPO_GESTAO, "Cientec", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(17, "Contratos Terceirizados na Adm. Pública", "Em oferta", 35, "12/11/2026", "13/11/2026", "09h00 às 16h00", "Online (Zoom)", 410.00, 14, "Dr. Rafael Borges", "Fiscalização de contratos e responsabilidade trabalhista.", Evento.TIPO_JURIDICO, "Cientec", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(18, "Introdução à Inteligência Artificial", "Em andamento", 0, "10/05/2026", "12/05/2026", "19h00 às 22h00", "Sede Cientec – BH", 600.00, 9, "Eng. Lucas Ferreira", "Redes neurais e aprendizado de máquina supervisionado.", Evento.TIPO_TI, "Cientec", docs(Evento.TIPO_TI)));

        // =====================================================================
        // ── FUNARBE (IDs 19 a 35) ── 17 Eventos (13 Em Oferta / 4 Em Andamento)
        // =====================================================================
        lista.add(new Evento(19, "Prática Jurídica no Novo CPC", "Em oferta", 30, "22/10/2026", "25/10/2026", "18h30 às 21h30", "Auditório do Departamento de Direito - UFV", 250.00, 15, "Dr. Marcos Ribeiro", "Peticionamento estratégico nos tribunais.", Evento.TIPO_JURIDICO, "Funarbe", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(20, "Oratória e Comunicação Assertiva", "Em oferta", 15, "05/11/2026", "06/11/2026", "08h00 às 12h00", "Campus UFV - Sala de Extensão 202, Viçosa", 80.00, 12, "Profª. Ana Clara Ramos", "Linguagem corporal e técnicas de expressão.", Evento.TIPO_COMUNICACAO, "Funarbe", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(21, "Redação Oficial e Documentação Técnica", "Em andamento", 0, "12/05/2026", "14/05/2026", "14h00 às 17h00", "Online (Zoom)", 310.00, 9, "Prof.ª Carla Dutra", "Elaboração de ofícios e memorandos técnicos.", Evento.TIPO_COMUNICACAO, "Funarbe", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(22, "Direito Ambiental Aplicado ao Agro", "Em oferta", 45, "12/08/2026", "14/08/2026", "08h00 às 12h00", "Auditório CEE - UFV", 220.00, 12, "Dra. Heloísa Castro", "Licenciamento ambiental rural e Código Florestal.", Evento.TIPO_JURIDICO, "Funarbe", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(23, "Lógica de Programação com Python", "Em oferta", 40, "18/08/2026", "22/08/2026", "19h00 às 22h00", "Laboratório DPD - UFV", 150.00, 15, "Prof. Ricardo Silva", "Estruturas condicionais, loops e funções.", Evento.TIPO_TI, "Funarbe", docs(Evento.TIPO_TI)));
        lista.add(new Evento(24, "Estatística Experimental com R", "Em oferta", 20, "05/09/2026", "09/09/2026", "14h00 às 18h00", "Online (Microsoft Teams)", 190.00, 20, "Dr. Fernando Rocha", "Análise de variância (ANOVA) e regressões.", Evento.TIPO_TI, "Funarbe", docs(Evento.TIPO_TI)));
        lista.add(new Evento(25, "Comunicação Científica e Escrita de Artigos", "Em andamento", 0, "02/06/2026", "03/06/2026", "09h00 às 12h00", "Auditório BBV - UFV", 60.00, 6, "Profª. Carla Dutra", "Estruturação de abstracts e submissão internacional.", Evento.TIPO_COMUNICACAO, "Funarbe", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(26, "Propriedade Intelectual e Patentes", "Em oferta", 40, "15/10/2026", "16/10/2026", "14h00 às 18h00", "Sede da Funarbe - Viçosa", 130.00, 8, "Dra. Rita de Cássia", "Redação de patentes e busca no INPI.", Evento.TIPO_JURIDICO, "Funarbe", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(27, "Gestão de Projetos Financiados pela FAPEMIG", "Em andamento", 0, "10/05/2026", "11/05/2026", "08h30 às 12h30", "Online (Google Meet)", 0.00, 8, "Roberto Alencar", "Prestação de contas e relatórios de execução.", Evento.TIPO_GESTAO, "Funarbe", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(28, "Estruturas de Dados em C++", "Em oferta", 25, "03/11/2026", "06/11/2026", "19h00 às 22h00", "Laboratório DPD - UFV", 200.00, 12, "Prof. Ricardo Silva", "Ponteiros, listas encadeadas, árvores e grafos.", Evento.TIPO_TI, "Funarbe", docs(Evento.TIPO_TI)));
        lista.add(new Evento(29, "Direito do Trabalho no Setor Rural", "Em oferta", 30, "25/08/2026", "27/08/2026", "14h00 às 17h00", "Auditório CEE - UFV", 180.00, 9, "Dra. Heloísa Castro", "Contratos de safra, NR 31 e fiscalização.", Evento.TIPO_JURIDICO, "Funarbe", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(30, "Desenvolvimento de Dashboards com Shiny", "Em oferta", 15, "10/11/2026", "12/11/2026", "18h30 às 21h30", "Online (Zoom)", 240.00, 9, "Dr. Fernando Rocha", "Aplicações web interativas utilizando a linguagem R.", Evento.TIPO_TI, "Funarbe", docs(Evento.TIPO_TI)));
        lista.add(new Evento(31, "Mediação de Conflitos Acadêmicos", "Em andamento", 0, "05/05/2026", "06/05/2026", "09h00 às 12h00", "Campus UFV - Viçosa", 50.00, 6, "Profª. Ana Clara Ramos", "Escuta ativa aplicada às relações universitárias.", Evento.TIPO_COMUNICACAO, "Funarbe", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(32, "Elaboração de Relatórios Técnicos", "Em oferta", 30, "01/10/2026", "02/10/2026", "14h00 às 18h00", "Online (Microsoft Teams)", 90.00, 8, "Prof.ª Carla Dutra", "Normas ABNT e concisão na linguagem escrita.", Evento.TIPO_COMUNICACAO, "Funarbe", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(33, "Introdução ao Linux e Linha de Comando", "Em oferta", 35, "11/08/2026", "13/08/2026", "19h00 às 22h00", "Laboratório DPD - UFV", 100.00, 9, "Prof. Ricardo Silva", "Manipulação de arquivos, permissões e scripts bash.", Evento.TIPO_TI, "Funarbe", docs(Evento.TIPO_TI)));
        lista.add(new Evento(34, "Legislação para Startups e Inovação", "Em oferta", 35, "19/11/2026", "20/11/2026", "14h00 às 17h00", "Sede da Funarbe - Viçosa", 150.00, 6, "Dra. Rita de Cássia", "Marco Legal das Startups e contratos de mútuo.", Evento.TIPO_JURIDICO, "Funarbe", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(35, "Gestão Orçamentária de Projetos", "Em oferta", 20, "16/09/2026", "17/09/2026", "08h30 às 12h30", "Online (Google Meet)", 110.00, 8, "Roberto Alencar", "Planejamento financeiro de rubricas de custeio.", Evento.TIPO_GESTAO, "Funarbe", docs(Evento.TIPO_GESTAO)));

        // =====================================================================
        // ── FACEV (IDs 36 a 52) ── 17 Eventos (13 Em Oferta / 4 Em Andamento)
        // =====================================================================
        lista.add(new Evento(36, "Elaboração de Projetos Sociais e Captação", "Em oferta", 50, "01/10/2026", "03/10/2026", "09h00 às 17h00", "Sede Facev - Centro de Viçosa", 120.00, 24, "Prof. Rodrigo Andrade", "Como estruturar projetos para editais públicos.", Evento.TIPO_GESTAO, "Facev", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(37, "Mediação e Resolução de Conflitos", "Em oferta", 30, "15/10/2026", "16/10/2026", "13h30 às 17h30", "Câmara Municipal de Viçosa - Sala de Reuniões", 0.00, 8, "Dra. Gláucia Silva", "Técnicas de comunicação não-violenta comunitária.", Evento.TIPO_COMUNICACAO, "Facev", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(38, "Inclusão Digital e Acessibilidade Web", "Em oferta", 20, "20/11/2026", "22/11/2026", "18h00 às 21h00", "Laboratório do Departamento de Informática - UFV", 50.00, 9, "Engª. Aline Costa", "Técnicas de desenvolvimento focadas em acessibilidade.", Evento.TIPO_TI, "Facev", docs(Evento.TIPO_TI)));
        lista.add(new Evento(39, "Análise de Custos e Formação de Preço", "Em andamento", 0, "10/05/2026", "12/05/2026", "19h00 às 22h00", "Sede Facev - Viçosa", 210.00, 9, "Prof. Juliano Dias", "Custos fixos, variáveis, margem de contribuição e ponto de equilíbrio.", Evento.TIPO_GESTAO, "Facev", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(40, "Marketing Digital para Cooperativas", "Em oferta", 40, "05/09/2026", "06/09/2026", "08h00 às 12h00", "Online (Google Meet)", 90.00, 8, "Mariana Linhares", "Estratégias de tráfego orgânico, branding e redes sociais.", Evento.TIPO_COMUNICACAO, "Facev", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(41, "Direito do Consumidor para Pequenas Empresas", "Em andamento", 0, "18/05/2026", "19/05/2026", "14h00 às 17h00", "Online (Zoom)", 140.00, 6, "Dr. Silveira Neto", "Práticas comerciais abusivas, trocas e garantias do CDC.", Evento.TIPO_JURIDICO, "Facev", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(42, "Programação Web Básica com HTML e CSS", "Em oferta", 30, "14/10/2026", "16/10/2026", "19h00 às 22h00", "Sede Facev - Viçosa", 120.00, 9, "Engª. Aline Costa", "Estruturação de páginas estáticas e estilização moderna.", Evento.TIPO_TI, "Facev", docs(Evento.TIPO_TI)));
        lista.add(new Evento(43, "Planejamento Financeiro Familiar", "Em andamento", 0, "24/04/2026", "24/04/2026", "19h00 às 22h00", "Câmara Municipal de Viçosa", 0.00, 3, "Prof. Juliano Dias", "Orçamento pessoal, controle de dívidas e investimentos básicos.", Evento.TIPO_GESTAO, "Facev", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(44, "Técnicas de Negociação e Vendas", "Em oferta", 25, "08/11/2026", "09/11/2026", "13h30 às 17h30", "Sede Facev - Viçosa", 160.00, 8, "Mariana Linhares", "Gatilhos mentais, objeções e fechamento de vendas.", Evento.TIPO_COMUNICACAO, "Facev", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(45, "Introdução ao Direito de Propriedade", "Em oferta", 30, "03/09/2026", "04/09/2026", "18h30 às 21h30", "Online (Zoom)", 190.00, 6, "Dr. Silveira Neto", "Regularização fundiária urbana (REURB) e usucapião.", Evento.TIPO_JURIDICO, "Facev", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(46, "JavaScript Essencial para Iniciantes", "Em oferta", 20, "28/11/2026", "29/11/2026", "09h00 às 12h00", "Online (Teams)", 180.00, 6, "Engª. Aline Costa", "Manipulação do DOM, variáveis, arrays e eventos.", Evento.TIPO_TI, "Facev", docs(Evento.TIPO_TI)));
        lista.add(new Evento(47, "Gestão de Pequenos Negócios", "Em oferta", 15, "02/09/2026", "04/09/2026", "14h00 às 18h00", "Sede Facev - Viçosa", 250.00, 12, "Prof. Juliano Dias", "Fluxo de caixa, estoque e compras para microempresas.", Evento.TIPO_GESTAO, "Facev", docs(Evento.TIPO_GESTAO)));
        lista.add(new Evento(48, "Comunicação Interna e Endomarketing", "Em oferta", 35, "05/12/2026", "05/12/2026", "08h00 às 17h00", "Online (Google Meet)", 110.00, 8, "Mariana Linhares", "Engajamento de colaboradores e canais corporativos.", Evento.TIPO_COMUNICACAO, "Facev", docs(Evento.TIPO_COMUNICACAO)));
        lista.add(new Evento(49, "Contratos Cíveis e Comerciais Básicos", "Em oferta", 40, "11/10/2026", "12/10/2026", "19h00 às 22h00", "Online (Zoom)", 210.00, 6, "Dr. Silveira Neto", "Estrutura dos contratos de prestação de serviços e compra e venda.", Evento.TIPO_JURIDICO, "Facev", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(50, "UX/UI Design: Fundamentos de Interface", "Em oferta", 15, "12/12/2026", "13/12/2026", "13h30 às 17h30", "Sede Facev - Viçosa", 290.00, 8, "Engª. Aline Costa", "Criação de wireframes e protótipos interativos no Figma.", Evento.TIPO_TI, "Facev", docs(Evento.TIPO_TI)));
        lista.add(new Evento(51, "Auditoria Trabalhista Preventiva", "Em andamento", 0, "15/05/2026", "16/05/2026", "09h00 às 16h00", "Sede Facev - Viçosa", 320.00, 14, "Dr. Silveira Neto", "Prevenção de passivos trabalhistas e análise de folhas de pagamento.", Evento.TIPO_JURIDICO, "Facev", docs(Evento.TIPO_JURIDICO)));
        lista.add(new Evento(52, "Comunicação Não-Violenta em Equipes", "Em oferta", 40, "18/12/2026", "19/12/2026", "09h00 às 12h00", "Online (Google Meet)", 90.00, 6, "Dra. Gláucia Silva", "Quatro componentes da CNV aplicados ao ambiente corporativo.", Evento.TIPO_COMUNICACAO, "Facev", docs(Evento.TIPO_COMUNICACAO)));

        // ── Inicializa vagas no banco (só na primeira execução) ──────
        for (Evento e : lista) {
            db.inicializarVagasSeNecessario(e.getId(), e.getVagas());
        }

        // ── Validador de Data Atual e Virada de Status Dinâmica ──
        SimpleDateFormat parserData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dataHoje = new Date();

        for (Evento e : lista) {
            try {
                Date dataInicioCurso = parserData.parse(e.getDataInicio());
                if (dataInicioCurso != null && dataInicioCurso.before(dataHoje)) {
                    // Se a data do curso já passou, zera as vagas (comportamento de andamento)
                    e.setVagas(0);
                } else if (!e.getSituacao().equalsIgnoreCase("Em andamento")) {
                    // Sincroniza do SQLite normalmente
                    int vagasBanco = db.getVagasDisponiveis(e.getId());
                    e.setVagas(vagasBanco);
                }
            } catch (Exception ignored) {
                if (!e.getSituacao().equalsIgnoreCase("Em andamento")) {
                    int vagasBanco = db.getVagasDisponiveis(e.getId());
                    e.setVagas(vagasBanco);
                }
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