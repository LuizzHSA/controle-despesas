import java.time.LocalDate;

/**
 * Subclasse Eventual - gastos não recorrentes.
 */
public class Eventual extends Despesa {
    private String motivo;

    public Eventual(String descricao, double valorTotal, LocalDate dataVencimento, TipoDespesa tipo, String motivo) {
        super(descricao, valorTotal, dataVencimento, tipo);
        this.motivo = motivo;
    }

    public Eventual(int id, String descricao, double valorTotal, double valorPago,
            LocalDate dataVencimento, TipoDespesa tipo, boolean paga, String motivo) {
        super(id, descricao, valorTotal, valorPago, dataVencimento, tipo, paga);
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String categoriaEspecifica() {
        return "Eventual - motivo: " + motivo;
    }

    @Override
    public String toString() {
        return super.toString() + " | Motivo: " + motivo;
    }
}
