package service;

import repository.UsuarioRepository;
import util.Criptografia;
import model.Usuario;
import java.util.*;

public class UsuarioService {
    private final UsuarioRepository repo = new UsuarioRepository();

    public void cadastrar(String login, String senhaPura) {
        String hash = Criptografia.sha256(senhaPura);
        Usuario u = new Usuario(login, hash);
        repo.salvar(u);
        System.out.println("👤 Usuário cadastrado: " + login);
    }

    public List<Usuario> listar() {
        return repo.listar();
    }
}
