package repository;

import java.util.*;
import util.FileUtils;
import model.TipoDespesa;

public class TipoDespesaRepository {
    private static final String ARQ = "data/tipos_despesa.txt";

    public void salvar(TipoDespesa t) {
        FileUtils.appendLine(ARQ, t.toFileString());
    }

    public List<TipoDespesa> listar() {
        List<String> lines = FileUtils.readLines(ARQ);
        List<TipoDespesa> tipos = new ArrayList<>();
        for (String l : lines) {
            tipos.add(new TipoDespesa(l.trim()));
        }
        return tipos;
    }
}