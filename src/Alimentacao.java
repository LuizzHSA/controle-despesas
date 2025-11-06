import java.time.LocalDate;

/**
 * Subclasse Alimentacao - demonstra herança e sobrescrita.
 */
public class Alimentacao extends Despesa {
    private String local;

    public Alimentacao(String descricao, double valorTotal, LocalDate dataVencimento, TipoDespesa tipo, String local) {
        super(descricao, valorTotal, dataVencimento, tipo);
        this.local = local;
    }

    public Alimentacao(int id, String descricao, double valorTotal, double valorPago,
            LocalDate dataVencimento, TipoDespesa tipo, boolean paga, String local) {
        super(id, descricao, valorTotal, valorPago, dataVencimento, tipo, paga);
        this.local = local;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String categoriaEspecifica() {
        return "Alimentação - local: " + local;
    }

    @Override
    public String toString() {
        return super.toString() + " | Local: " + local;
    }
}
