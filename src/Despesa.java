import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe abstrata Despesa - contém comportamentos comuns e contador estático.
 */
public abstract class Despesa implements Pagavel {
    private static int contadorGlobal = 0;

    protected int id;
    protected String descricao;
    protected double valorTotal;
    protected double valorPago;
    protected LocalDate dataVencimento;
    protected TipoDespesa tipo;
    protected boolean paga;

    // Construtor para criar nova despesa (id automático)
    public Despesa(String descricao, double valorTotal, LocalDate dataVencimento, TipoDespesa tipo) {
        this.id = ++contadorGlobal;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.valorPago = 0.0;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = false;
    }

    // Construtor usado ao carregar de arquivo (preserva id e estado)
    public Despesa(int id, String descricao, double valorTotal, double valorPago,
            LocalDate dataVencimento, TipoDespesa tipo, boolean paga) {
        this.id = id;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.valorPago = valorPago;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = paga;
        // ajustar contador global para não gerar ids duplicados
        if (id > contadorGlobal)
            contadorGlobal = id;
    }

    public static int getContadorGlobal() {
        return contadorGlobal;
    }

    public static void setContadorGlobal(int v) {
        contadorGlobal = v;
    }

    // getters e setters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public TipoDespesa getTipo() {
        return tipo;
    }

    public void setTipo(TipoDespesa tipo) {
        this.tipo = tipo;
    }

    public boolean isPaga() {
        return paga;
    }

    public double valorRestante() {
        return Math.max(0.0, valorTotal - valorPago);
    }

    @Override
    public void anotarPagamento(double valorPago, LocalDate dataPagamento) {
        if (valorPago <= 0) {
            System.out.println("Valor inválido. Deve ser maior que zero.");
            return;
        }
        this.valorPago += valorPago;
        if (this.valorPago >= this.valorTotal) {
            this.paga = true;
            this.valorPago = this.valorTotal;
        }
        System.out.printf("Pagamento R$ %.2f registrado para ID %d em %s%n",
                valorPago, id, dataPagamento.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    // Formato de gravação em arquivo:
    // id;tipoNome;descricao;valorTotal;valorPago;dataVencimento;status
    public String toRecord() {
        // substituir ';' em texto para não quebrar o CSV simples
        String safeDesc = descricao.replace(";", ",");
        return String.format("%d;%s;%s;%.2f;%.2f;%s;%s",
                id,
                tipo.getNome(),
                safeDesc,
                valorTotal,
                valorPago,
                dataVencimento.format(DateTimeFormatter.ISO_LOCAL_DATE),
                (paga ? "PAGA" : "PENDENTE"));
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s | R$%.2f (Pago R$%.2f) | Venc: %s | Tipo: %s | %s",
                id, descricao, valorTotal, valorPago,
                dataVencimento.format(DateTimeFormatter.ISO_LOCAL_DATE),
                tipo.getNome(), (paga ? "PAGA" : "PENDENTE"));
    }

    // método abstrato para descrição específica de categoria
    public abstract String categoriaEspecifica();
}
