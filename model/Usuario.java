package model;

public class Usuario {
    private String login;
    private String senhaHash;

    public Usuario(String login, String senhaHash) {
        this.login = login;
        this.senhaHash = senhaHash;
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String toFileString() {
        // login;senhaHash
        return login + ";" + senhaHash;
    }

    @Override
    public String toString() {
        return "Usuario: " + login;
    }
}
