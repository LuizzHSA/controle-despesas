import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static DespesaService despesaService = new DespesaService();
    private static TipoDespesaService tipoDespesaService = new TipoDespesaService();
    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   SISTEMA DE CONTROLE DE DESPESAS");
        System.out.println("=========================================\n");

        // Login simples antes de entrar no menu
        if (!fazerLogin()) {
            System.out.println("Encerrando o sistema...");
            return;
        }

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    entrarDespesa();
                    break;
                case 2:
                    anotarPagamento();
                    break;
                case 3:
                    listarDespesasPorStatus(false);
                    break;
                case 4:
                    listarDespesasPorStatus(true);
                    break;
                case 5:
                    gerenciarTiposDespesa();
                    break;
                case 6:
                    gerenciarUsuarios();
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 7);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Entrar Despesa");
        System.out.println("2. Anotar Pagamento");
        System.out.println("3. Listar Despesas em Aberto");
        System.out.println("4. Listar Despesas Pagas");
        System.out.println("5. Gerenciar Tipos de Despesa");
        System.out.println("6. Gerenciar Usuários");
        System.out.println("7. Sair");
        System.out.println("==========================");
    }

    // ---------------- LOGIN ----------------

    private static boolean fazerLogin() {
        System.out.println("==== LOGIN ====");
        System.out.print("Usuário: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (usuarioService.verificarLogin(login, senha)) {
            System.out.println("Login realizado com sucesso!");
            return true;
        } else {
            System.out.println("Login inválido.");
            return false;
        }
    }

    // ---------------- DESPESAS ----------------

    private static void entrarDespesa() {
        System.out.println("\n=== ENTRAR DESPESA ===");
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        double valor = lerDouble("Valor: ");
        System.out.print("Data de vencimento (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        System.out.println("Selecione o tipo de despesa:");
        tipoDespesaService.listarTipos();
        int tipoId = lerInteiro("ID do tipo: ");

        TipoDespesa tipo = tipoDespesaService.buscarPorId(tipoId);
        if (tipo == null) {
            System.out.println("Tipo inválido!");
            return;
        }

        Despesa despesa = new Despesa(descricao, valor, data, tipo);
        despesaService.inserir(despesa);
        System.out.println("Despesa cadastrada com sucesso!");
    }

    private static void anotarPagamento() {
        System.out.println("\n=== ANOTAR PAGAMENTO ===");
        despesaService.listarTodas();

        int id = lerInteiro("ID da despesa: ");
        double valor = lerDouble("Valor do pagamento: ");
        System.out.print("Data do pagamento (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        boolean ok = despesaService.anotarPagamento(id, valor, data);
        if (ok) {
            System.out.println("Pagamento registrado!");
        } else {
            System.out.println("Erro ao registrar pagamento. Verifique o ID e valores.");
        }
    }

    private static void listarDespesasPorStatus(boolean pagas) {
        System.out.println(pagas ? "\n=== DESPESAS PAGAS ===" : "\n=== DESPESAS EM ABERTO ===");
        List<Despesa> lista = despesaService.listarPorStatus(pagas);
        for (Despesa d : lista) {
            System.out.println(d);
        }
    }

    // ---------------- TIPOS DE DESPESA ----------------

    private static void gerenciarTiposDespesa() {
        System.out.println("\n=== GERENCIAR TIPOS DE DESPESA ===");
        System.out.println("1. Criar novo tipo");
        System.out.println("2. Listar tipos");
        System.out.println("3. Excluir tipo");
        int opcao = lerInteiro("Escolha: ");

        switch (opcao) {
            case 1:
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Descrição: ");
                String desc = scanner.nextLine();
                tipoDespesaService.criarTipo(new TipoDespesa(nome, desc));
                break;
            case 2:
                tipoDespesaService.listarTipos();
                break;
            case 3:
                int id = lerInteiro("ID do tipo a excluir: ");
                tipoDespesaService.excluirTipo(id);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ---------------- USUÁRIOS ----------------

    private static void gerenciarUsuarios() {
        System.out.println("\n=== GERENCIAR USUÁRIOS ===");
        System.out.println("1. Cadastrar novo usuário");
        System.out.println("2. Listar usuários");
        int opcao = lerInteiro("Escolha: ");

        switch (opcao) {
            case 1:
                System.out.print("Login: ");
                String login = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                usuarioService.cadastrarUsuario(login, senha);
                break;
            case 2:
                usuarioService.listarUsuarios();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ---------------- MÉTODOS DE APOIO ----------------

    private static int lerInteiro(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, digite um número inteiro válido.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpa buffer
        return valor;
    }

    private static double lerDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, digite um número válido.");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine(); // limpa buffer
        return valor;
    }
}
