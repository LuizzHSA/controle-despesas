import java.time.LocalDate;

/**
 * Interface Pagavel - contrato para objetos que podem receber pagamento.
 */
public interface Pagavel {
    void anotarPagamento(double valorPago, LocalDate dataPagamento);
}
