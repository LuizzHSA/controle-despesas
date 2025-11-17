package model;

public class Eventual extends Despesa {
    public Eventual(String descricao, double valor, String dataVencimento) {
        super(descricao, valor, dataVencimento, "Eventual");
    }

    @Override
    public void registrarPagamento(double valor, String dataPagamento) {
        super.registrarPagamento(valor, dataPagamento);
        System.out.println("🧾 Despesa eventual registrada como paga.");
    }
}
