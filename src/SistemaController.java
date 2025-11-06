import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SistemaController - orquestra a lógica, persistência e operações.
 */
public class SistemaController {
    private static final String PATH_DESPESAS = "data/despesas.txt";
    private static final String PATH_TIPOS = "data/tipos.txt";
    private static final String PATH_USUARIOS = "data/usuarios.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

    private List<Despesa> despesas = new ArrayList<>();
    private List<TipoDespesa> tipos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    private int ultimoTipoId = 0;
    private int ultimoUsuarioId = 0;

    public SistemaController() {
        carregarTipos();
        carregarUsuarios();
        carregarDespesas();
    }

    // ------------------ TIPOS ------------------
    private void carregarTipos() {
        List<String> linhas = ArquivoUtil.lerLinhas(PATH_TIPOS);
        tipos.clear();
        for (String l : linhas) {
            if (l.trim().isEmpty())
                continue;
            String[] f = l.split(";", -1);
            try {
                int id = Integer.parseInt(f[0]);
                String nome = f[1];
                String desc = f.length > 2 ? f[2] : "";
                tipos.add(new TipoDespesa(id, nome, desc));
                ultimoTipoId = Math.max(ultimoTipoId, id);
            } catch (Exception ex) {
                System.out.println("Linha tipo inválida: " + l);
            }
        }
    }

    public void listarTipos() {
        if (tipos.isEmpty()) {
            System.out.println("Nenhum tipo cadastrado.");
            return;
        }
        tipos.forEach(System.out::println);
    }

    public TipoDespesa buscarTipoPorId(int id) {
        return tipos.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public TipoDespesa obterOuCriarTipoPorNome(String nome) {
        Optional<TipoDespesa> op = tipos.stream()
                .filter(t -> t.getNome().equalsIgnoreCase(nome)).findFirst();
        if (op.isPresent())
            return op.get();
        int id = ++ultimoTipoId;
        TipoDespesa t = new TipoDespesa(id, nome, "");
        tipos.add(t);
        ArquivoUtil.appendLinha(PATH_TIPOS, t.toRecord());
        return t;
    }

    public void criarTipo(String nome, String descricao) {
        int id = ++ultimoTipoId;
        TipoDespesa t = new TipoDespesa(id, nome, descricao);
        tipos.add(t);
        ArquivoUtil.appendLinha(PATH_TIPOS, t.toRecord());
        System.out.println("Tipo criado: " + t);
    }

    public void editarTipo(int id, String novoNome, String novaDesc) {
        TipoDespesa t = buscarTipoPorId(id);
        if (t == null) {
            System.out.println("Tipo não encontrado.");
            return;
        }
        t.setNome(novoNome);
        t.setDescricao(novaDesc);
        gravarTodosTipos();
        System.out.println("Tipo atualizado: " + t);
    }

    public void excluirTipo(int id) {
        TipoDespesa t = buscarTipoPorId(id);
        if (t == null) {
            System.out.println("Tipo não encontrado.");
            return;
        }
        tipos.remove(t);
        gravarTodosTipos();
        System.out.println("Tipo excluído: ID " + id);
    }

    private void gravarTodosTipos() {
        List<String> lines = tipos.stream().map(TipoDespesa::toRecord).collect(Collectors.toList());
        ArquivoUtil.sobrescrever(PATH_TIPOS, lines);
    }

    // ------------------ USUARIOS ------------------
    private void carregarUsuarios() {
        List<String> linhas = ArquivoUtil.lerLinhas(PATH_USUARIOS);
        usuarios.clear();
        for (String l : linhas) {
            if (l.trim().isEmpty())
                continue;
            String[] f = l.split(";", -1);
            try {
                int id = Integer.parseInt(f[0]);
                String login = f[1];
                String hash = f[2];
                usuarios.add(new Usuario(id, login, hash));
                ultimoUsuarioId = Math.max(ultimoUsuarioId, id);
            } catch (Exception ex) {
                System.out.println("Linha usuário inválida: " + l);
            }
        }
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        usuarios.forEach(System.out::println);
    }

    public void cadastrarUsuario(String login, String senha) {
        int id = ++ultimoUsuarioId;
        String hash = CriptografiaUtil.sha256(senha);
        Usuario u = new Usuario(id, login, hash);
        usuarios.add(u);
        ArquivoUtil.appendLinha(PATH_USUARIOS, u.toRecord());
        System.out.println("Usuário cadastrado: " + u);
    }

    public void editarUsuario(int id, String novoLogin, String novaSenha) {
        Usuario u = usuarios.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (u == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        String hash = CriptografiaUtil.sha256(novaSenha);
        usuarios.remove(u);
        Usuario novo = new Usuario(id, novoLogin, hash);
        usuarios.add(novo);
        gravarTodosUsuarios();
        System.out.println("Usuário atualizado: " + novo);
    }

    public void excluirUsuario(int id) {
        Usuario u = usuarios.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (u == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        usuarios.remove(u);
        gravarTodosUsuarios();
        System.out.println("Usuário excluído: ID " + id);
    }

    private void gravarTodosUsuarios() {
        List<String> lines = usuarios.stream().map(Usuario::toRecord).collect(Collectors.toList());
        ArquivoUtil.sobrescrever(PATH_USUARIOS, lines);
    }

    public boolean autenticar(String login, String senha) {
        String hash = CriptografiaUtil.sha256(senha);
        return usuarios.stream().anyMatch(u -> u.getLogin().equals(login) && u.getSenhaHash().equals(hash));
    }

    // ------------------ DESPESAS ------------------
    private void carregarDespesas() {
        List<String> linhas = ArquivoUtil.lerLinhas(PATH_DESPESAS);
        despesas.clear();
        int maxId = 0;
        for (String l : linhas) {
            if (l.trim().isEmpty())
                continue;
            String[] f = l.split(";", -1);
            try {
                int id = Integer.parseInt(f[0]);
                String tipoNome = f[1];
                String descricao = f[2];
                double valorTotal = Double.parseDouble(f[3]);
                double valorPago = Double.parseDouble(f[4]);
                LocalDate dataVenc = LocalDate.parse(f[5], dtf);
                boolean paga = f[6].equalsIgnoreCase("PAGA");

                TipoDespesa tipo = tipos.stream()
                        .filter(t -> t.getNome().equalsIgnoreCase(tipoNome)).findFirst()
                        .orElse(new TipoDespesa(++ultimoTipoId, tipoNome, ""));

                // Escolha de subclasse pela string do tipo
                Despesa d;
                String tn = tipo.getNome().toLowerCase();
                if (tn.contains("transport")) {
                    d = new Transporte(id, descricao, valorTotal, valorPago, dataVenc, tipo, paga, "Não informado");
                } else if (tn.contains("aliment")) {
                    d = new Alimentacao(id, descricao, valorTotal, valorPago, dataVenc, tipo, paga, "Não informado");
                } else {
                    d = new Eventual(id, descricao, valorTotal, valorPago, dataVenc, tipo, paga, "Não informado");
                }

                despesas.add(d);
                maxId = Math.max(maxId, id);
            } catch (Exception ex) {
                System.out.println("Linha despesa inválida: " + l);
            }
        }
        Despesa.setContadorGlobal(Math.max(Despesa.getContadorGlobal(), maxId));
    }

    public void listarDespesasTodas() {
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
            return;
        }
        despesas.forEach(System.out::println);
    }

    public void listarDespesasPorStatusEPeriodo(boolean pagas, LocalDate inicio, LocalDate fim) {
        List<Despesa> lista = despesas.stream()
                .filter(d -> d.isPaga() == pagas)
                .filter(d -> !d.getDataVencimento().isBefore(inicio) && !d.getDataVencimento().isAfter(fim))
                .collect(Collectors.toList());
        if (lista.isEmpty())
            System.out.println("Nenhuma despesa encontrada para os critérios.");
        else
            lista.forEach(System.out::println);
    }

    public void listarDespesasPorCategoria(String categoria) {
        List<Despesa> lista = despesas.stream()
                .filter(d -> d.getTipo().getNome().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
        if (lista.isEmpty())
            System.out.println("Nenhuma despesa encontrada para essa categoria.");
        else
            lista.forEach(System.out::println);
    }

    public Despesa buscarDespesaPorId(int id) {
        return despesas.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
    }

    public void inserirDespesa(Despesa d) {
        despesas.add(d);
        ArquivoUtil.appendLinha(PATH_DESPESAS, d.toRecord());
        System.out.println("Despesa adicionada: " + d);
    }

    public void anotarPagamento(int id, double valorPago, LocalDate dataPagamento) {
        Despesa d = buscarDespesaPorId(id);
        if (d == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }
        d.anotarPagamento(valorPago, dataPagamento);
        gravarTodasDespesas();
    }

    public void editarDespesa(int id, String novaDescricao, Double novoValor, LocalDate novaData,
            TipoDespesa novoTipo) {
        Despesa d = buscarDespesaPorId(id);
        if (d == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }
        if (novaDescricao != null)
            d.setDescricao(novaDescricao);
        if (novoValor != null)
            d.setValorTotal(novoValor);
        if (novaData != null)
            d.setDataVencimento(novaData);
        if (novoTipo != null)
            d.setTipo(novoTipo);
        gravarTodasDespesas();
        System.out.println("Despesa editada: " + d);
    }

    public void excluirDespesa(int id) {
        Despesa d = buscarDespesaPorId(id);
        if (d == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }
        despesas.remove(d);
        gravarTodasDespesas();
        System.out.println("Despesa excluída: ID " + id);
    }

    private void gravarTodasDespesas() {
        List<String> lines = despesas.stream().map(Despesa::toRecord).collect(Collectors.toList());
        ArquivoUtil.sobrescrever(PATH_DESPESAS, lines);
    }
}
