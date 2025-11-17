package service;

import repository.TipoDespesaRepository;
import model.TipoDespesa;
import java.util.*;

public class TipoDespesaService {
    private final TipoDespesaRepository repo = new TipoDespesaRepository();

    public void adicionar(String nome) {
        repo.salvar(new TipoDespesa(nome));
        System.out.println("✅ Tipo adicionado: " + nome);
    }

    public List<TipoDespesa> listar() {
        return repo.listar();
    }
}