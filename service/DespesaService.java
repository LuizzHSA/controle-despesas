package service;

import repository.DespesaRepository;
import model.*;
import java.util.*;

public class DespesaService {
    private final DespesaRepository repo = new DespesaRepository();

    public void adicionar(String tipo, String descricao, double valor, String dataVencimento) {
        Despesa d;
        switch (tipo) {
            case "Alimentacao": d = new Alimentacao(descricao, valor, dataVencimento); break;
            case "Transporte": d = new Transporte(descricao, valor, dataVencimento); break;
            case "Eventual": d = new Eventual(descricao, valor, dataVencimento); break;
            case "Superfluo": d = new Superfluo(descricao, valor, dataVencimento); break;
            default: throw new IllegalArgumentException("Tipo inválido: " + tipo);
        }
        repo.salvar(d);
        System.out.println("✅ Despesa adicionada: " + d);
    }

    public List<Despesa> listar() {
        return repo.listar();
    }

    public void pagarPorDescricao(String descricao) {
        boolean ok = repo.registrarPagamentoPorDescricao(descricao);
        if (ok) System.out.println("💰 Pagamento registrado para: " + descricao);
        else System.out.println("⚠️ Despesa não encontrada ou já paga: " + descricao);
    }
}
