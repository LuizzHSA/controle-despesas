package repository;

import java.util.*;
import util.FileUtils;
import model.Usuario;

public class UsuarioRepository {
    private static final String ARQ = "data/usuarios.txt";

    public void salvar(Usuario u) {
        FileUtils.appendLine(ARQ, u.toFileString());
    }

    public List<Usuario> listar() {
        List<String> lines = FileUtils.readLines(ARQ);
        List<Usuario> usuarios = new ArrayList<>();
        for (String l : lines) {
            String[] p = l.split(";");
            if (p.length >= 2) {
                usuarios.add(new Usuario(p[0], p[1]));
            }
        }
        return usuarios;
    }
}
