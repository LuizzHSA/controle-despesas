import java.util.*;
import service.*;
import model.*;

public class Main {
    // ... existing code ...
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DespesaService despesaService = new DespesaService();
        UsuarioService usuarioService = new UsuarioService();
        TipoDespesaService tipoService = new TipoDespesaService();

        int opcao;
        do {
            System.out.println("\n====================================");
            System.out.println("  SISTEMA DE CONTROLE DE DESPESAS ");
            System.out.println("====================================");
            System.out.println("1. Entrar Despesa");
            System.out.println("2. Anotar Pagamento");
            System.out.println("3. Listar Despesas");
            System.out.println("4. Gerenciar Tipos de Despesa");
            System.out.println("5. Gerenciar Usuarios");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opcao: ");

            opcao = lerInt(sc);

            switch (opcao) {
                case 1 -> entrarDespesa(sc, despesaService);
                case 2 -> anotarPagamento(sc, despesaService);
                case 3 -> listarDespesas(despesaService);
                case 4 -> gerenciarTipos(sc, tipoService);
                case 5 -> gerenciarUsuarios(sc, usuarioService);
                case 0 -> System.out.println(" Encerrando o sistema... até mais!");
                default -> System.out.println(" Opção inválida! Tente novamente.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static int lerInt(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static void entrarDespesa(Scanner sc, DespesaService service) {
        System.out.print("Tipo (Alimentacao/Transporte/Eventual/Superfluo): ");
        String tipo = sc.nextLine().trim();
        System.out.print("Descrição: ");
        String desc = sc.nextLine().trim();
        System.out.print("Valor (ex: 123.45): ");
        double valor = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Data de vencimento (YYYY-MM-DD): ");
        String venc = sc.nextLine().trim();

        try {
            service.adicionar(tipo, desc, valor, venc);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar despesa: " + e.getMessage());
        }
    }

    private static void anotarPagamento(Scanner sc, DespesaService service) {
        System.out.print("Descrição da despesa a pagar: ");
        String desc = sc.nextLine().trim();
        service.pagarPorDescricao(desc);
    }

    private static void listarDespesas(DespesaService service) {
        List<Despesa> lista = service.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void gerenciarTipos(Scanner sc, TipoDespesaService service) {
        System.out.println("1. Adicionar tipo");
        System.out.println("2. Listar tipos");
        System.out.print("Escolha: ");
        int op = lerInt(sc);
        if (op == 1) {
            System.out.print("Nome do tipo: ");
            String nome = sc.nextLine().trim();
            service.adicionar(nome);
        } else if (op == 2) {
            List<TipoDespesa> tipos = service.listar();
            if (tipos.isEmpty())
                System.out.println("Nenhum tipo cadastrado.");
            else
                tipos.forEach(t -> System.out.println("- " + t.getNome()));
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void gerenciarUsuarios(Scanner sc, UsuarioService service) {
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Listar usuários");
        System.out.print("Escolha: ");
        int op = lerInt(sc);
        if (op == 1) {
            System.out.print("Login: ");
            String login = sc.nextLine().trim();
            System.out.print("Senha: ");
            String senha = sc.nextLine().trim();
            service.cadastrar(login, senha);
        } else if (op == 2) {
            List<Usuario> users = service.listar();
            if (users.isEmpty())
                System.out.println("Nenhum usuário cadastrado.");
            else
                users.forEach(System.out::println);
        } else {
            System.out.println("Opção inválida.");
        }
    }
    // ... existing code ...
}