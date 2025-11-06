/**
 * Representa um tipo/categoria de despesa.
 */
public class TipoDespesa {
    private int id;
    private String nome;
    private String descricao;

    public TipoDespesa(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public TipoDespesa(int id, String nome) {
        this(id, nome, "");
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String toRecord() {
        return String.format("%d;%s;%s", id, nome.replace(";", ","), descricao.replace(";", ","));
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s - %s", id, nome, descricao);
    }
}
