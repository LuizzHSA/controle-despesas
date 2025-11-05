/**
 * Representa um usuário com senha já hashed (SHA-256).
 */
public class Usuario {
    private int id;
    private String login;
    private String senhaHash;

    public Usuario(int id, String login, String senhaHash) {
        this.id = id;
        this.login = login;
        this.senhaHash = senhaHash;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String toRecord() {
        return String.format("%d;%s;%s", id, login.replace(";", ","), senhaHash);
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s", id, login);
    }
}
