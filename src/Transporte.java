import java.time.LocalDate;

/**
 * Subclasse Transporte - demonstra herança e sobrescrita.
 */
public class Transporte extends Despesa {
    private String meio;

    // Construtor novo (id automático)
    public Transporte(String descricao, double valorTotal, LocalDate dataVencimento, TipoDespesa tipo, String meio) {
        super(descricao, valorTotal, dataVencimento, tipo);
        this.meio = meio;
    }

    // Construtor ao carregar (preservando id/estado)
    public Transporte(int id, String descricao, double valorTotal, double valorPago,
            LocalDate dataVencimento, TipoDespesa tipo, boolean paga, String meio) {
        super(id, descricao, valorTotal, valorPago, dataVencimento, tipo, paga);
        this.meio = meio;
    }

    public String getMeio() {
        return meio;
    }

    public void setMeio(String meio) {
        this.meio = meio;
    }

    @Override
    public String categoriaEspecifica() {
        return "Transporte - meio: " + meio;
    }

    @Override
    public String toString() {
        return super.toString() + " | Meio: " + meio;
    }
}
