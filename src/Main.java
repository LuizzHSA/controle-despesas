import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Programa principal com menu que cumpre todos os requisitos.
 */
public class Main {
    private static final SistemaController sistema = new SistemaController();
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void main(String[] args) {
        System.out.println("=== Sistema de Controle de Despesas — v0.0.1 (entrega completa) ===");
        int opc;
        do {
            mostrarMenu();
            opc = lerInt("Escolha: ");
            switch (opc) {
                case 1 -> entrarDespesa();
                case 2 -> anotarPagamento();
                case 3 -> listarDespesasPeriodo(false);
                case 4 -> listarDespesasPeriodo(true);
                case 5 -> gerenciarTipos();
                case 6 -> gerenciarUsuarios();
                case 7 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opc != 7);
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Entrar Despesa");
        System.out.println("2. Anotar Pagamento");
        System.out.println("3. Listar Despesas em Aberto no período");
        System.out.println("4. Listar Despesas Pagas no período");
        System.out.println("5. Gerenciar Tipos de Despesa");
        System.out.println("6. Gerenciar Usuários");
        System.out.println("7. Sair");
    }

    // ---------- Entrar Despesa ----------
    private static void entrarDespesa() {
        System.out.println("\n--- ENTRAR DESPESA ---");
        String desc = lerTexto("Descrição: ");
        double valor = lerDouble("Valor: ");
        LocalDate venc = lerData("Data de vencimento (YYYY-MM-DD): ");
        sistema.listarTipos();
        String tipoNome = lerTexto("Nome do tipo (digite um existente ou novo): ");
        TipoDespesa tipo = sistema.obterOuCriarTipoPorNome(tipoNome);
        String detalhe = lerTexto("Detalhe específico (meio, local, motivo) - opcional: ");

        // escolher subclasse pela string do tipo
        String tn = tipo.getNome().toLowerCase();
        Despesa d;
        if (tn.contains("transport")) {
            d = new Transporte(desc, valor, venc, tipo, detalhe.isBlank() ? "Não informado" : detalhe);
        } else if (tn.contains("aliment")) {
            d = new Alimentacao(desc, valor, venc, tipo, detalhe.isBlank() ? "Não informado" : detalhe);
        } else {
            d = new Eventual(desc, valor, venc, tipo, detalhe.isBlank() ? "Não informado" : detalhe);
        }
        sistema.inserirDespesa(d);
    }

    // ---------- Anotar Pagamento ----------
    private static void anotarPagamento() {
        System.out.println("\n--- ANOTAR PAGAMENTO ---");
        sistema.listarDespesasTodas();
        int id = lerInt("ID da despesa: ");
        double valor = lerDouble("Valor do pagamento: ");
        LocalDate data = lerData("Data do pagamento (YYYY-MM-DD): ");
        sistema.anotarPagamento(id, valor, data);
    }

    // ---------- Listar (com submenu: Editar / Excluir / Voltar) ----------
    private static void listarDespesasPeriodo(boolean pagas) {
        System.out.println(pagas ? "\n--- DESPESAS PAGAS ---" : "\n--- DESPESAS EM ABERTO ---");
        LocalDate inicio = lerData("Data início (YYYY-MM-DD): ");
        LocalDate fim = lerData("Data fim (YYYY-MM-DD): ");
        sistema.listarDespesasPorStatusEPeriodo(pagas, inicio, fim);

        System.out.println("\nSubmenu: 1) Editar  2) Excluir  3) Voltar");
        int sub = lerInt("Escolha: ");
        if (sub == 1) {
            int id = lerInt("ID da despesa a editar: ");
            String novaDesc = lerTexto("Nova descrição (enter p/ manter): ");
            String novoValStr = lerTexto("Novo valor (enter p/ manter): ");
            Double novoVal = novoValStr.isBlank() ? null : Double.parseDouble(novoValStr.replace(",", "."));
            String novaDataStr = lerTexto("Nova data (YYYY-MM-DD) (enter p/ manter): ");
            LocalDate novaData = novaDataStr.isBlank() ? null : LocalDate.parse(novaDataStr, dtf);
            String novoTipoNome = lerTexto("Novo tipo (enter p/ manter): ");
            TipoDespesa novoTipo = novoTipoNome.isBlank() ? null : sistema.obterOuCriarTipoPorNome(novoTipoNome);
            sistema.editarDespesa(id, novaDesc.isBlank() ? null : novaDesc, novoVal, novaData, novoTipo);
        } else if (sub == 2) {
            int id = lerInt("ID da despesa a excluir: ");
            sistema.excluirDespesa(id);
        } else {
            System.out.println("Voltando ao menu.");
        }
    }

    // ---------- Gerenciar Tipos ----------
    private static void gerenciarTipos() {
        System.out.println("\n--- GERENCIAR TIPOS ---");
        System.out.println("1. Criar tipo");
        System.out.println("2. Listar tipos");
        System.out.println("3. Editar tipo");
        System.out.println("4. Excluir tipo");
        System.out.println("0. Voltar");
        int opc = lerInt("Escolha: ");
        switch (opc) {
            case 1 -> {
                String nome = lerTexto("Nome do tipo: ");
                String desc = lerTexto("Descrição (opcional): ");
                sistema.criarTipo(nome, desc);
            }
            case 2 -> sistema.listarTipos();
            case 3 -> {
                int id = lerInt("ID do tipo a editar: ");
                String novo = lerTexto("Novo nome: ");
                String desc = lerTexto("Nova descrição: ");
                sistema.editarTipo(id, novo, desc);
            }
            case 4 -> {
                int id = lerInt("ID do tipo a excluir: ");
                sistema.excluirTipo(id);
            }
            default -> System.out.println("Voltando.");
        }
    }

    // ---------- Gerenciar Usuários ----------
    private static void gerenciarUsuarios() {
        System.out.println("\n--- GERENCIAR USUÁRIOS ---");
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Listar usuários");
        System.out.println("3. Editar usuário");
        System.out.println("4. Excluir usuário");
        System.out.println("0. Voltar");
        int opc = lerInt("Escolha: ");
        switch (opc) {
            case 1 -> {
                String login = lerTexto("Login: ");
                String senha = lerTexto("Senha: ");
                sistema.cadastrarUsuario(login, senha);
            }
            case 2 -> sistema.listarUsuarios();
            case 3 -> {
                int id = lerInt("ID do usuário a editar: ");
                String novoLogin = lerTexto("Novo login: ");
                String novaSenha = lerTexto("Nova senha: ");
                sistema.editarUsuario(id, novoLogin, novaSenha);
            }
            case 4 -> {
                int id = lerInt("ID do usuário a excluir: ");
                sistema.excluirUsuario(id);
            }
            default -> System.out.println("Voltando.");
        }
    }

    // ---------- Helpers de leitura ----------
    private static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String s = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.println("Entrada inválida. Digite um número (ex: 123.45).");
            }
        }
    }

    private static String lerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static LocalDate lerData(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String s = sc.nextLine().trim();
                return LocalDate.parse(s, dtf);
            } catch (Exception e) {
                System.out.println("Data inválida. Use YYYY-MM-DD.");
            }
        }
    }
}
