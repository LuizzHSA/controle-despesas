package repository;

import java.util.*;
import util.FileUtils;
import model.*;

public class DespesaRepository {
    private static final String ARQ = "data/despesas.txt";

    public void salvar(Despesa d) {
        FileUtils.appendLine(ARQ, d.toFileString());
    }

    public List<Despesa> listar() {
        List<String> lines = FileUtils.readLines(ARQ);
        List<Despesa> despesas = new ArrayList<>();
        for (String l : lines) {
            // descricao;valor;dataVencimento;tipo;paga
            String[] p = l.split(";");
            if (p.length >= 5) {
                String desc = p[0];
                double val = Double.parseDouble(p[1]);
                String venc = p[2];
                String tipo = p[3];
                boolean paga = Boolean.parseBoolean(p[4]);
                Despesa d = criarDespesaPorTipo(tipo, desc, val, venc);
                if (d != null) {
                    if (paga) d.registrarPagamento(val, "import");
                    despesas.add(d);
                }
            }
        }
        return despesas;
    }

    public boolean registrarPagamentoPorDescricao(String descricao) {
        List<String> lines = FileUtils.readLines(ARQ);
        boolean alterou = false;
        List<String> novas = new ArrayList<>();
        for (String l : lines) {
            String[] p = l.split(";");
            if (p.length >= 5) {
                String desc = p[0];
                if (desc.equalsIgnoreCase(descricao)) {
                    double val = Double.parseDouble(p[1]);
                    String venc = p[2];
                    String tipo = p[3];
                    // marca como paga
                    novas.add(desc + ";" + val + ";" + venc + ";" + tipo + ";" + true);
                    alterou = true;
                } else {
                    novas.add(l);
                }
            } else {
                novas.add(l);
            }
        }
        if (alterou) FileUtils.writeLines(ARQ, novas);
        return alterou;
    }

    private Despesa criarDespesaPorTipo(String tipo, String descricao, double valor, String dataVencimento) {
        switch (tipo) {
            case "Alimentacao": return new Alimentacao(descricao, valor, dataVencimento);
            case "Transporte": return new Transporte(descricao, valor, dataVencimento);
            case "Eventual": return new Eventual(descricao, valor, dataVencimento);
            case "Superfluo": return new Superfluo(descricao, valor, dataVencimento);
            default: return null;
        }
    }
}
