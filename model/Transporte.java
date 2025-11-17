package model;

public class Transporte extends Despesa {
    public Transporte(String descricao, double valor, String dataVencimento) {
        super(descricao, valor, dataVencimento, "Transporte");
    }

    @Override
    public void registrarPagamento(double valor, String dataPagamento) {
        super.registrarPagamento(valor, dataPagamento);
        System.out.println("🚗 Despesa de transporte quitada com sucesso!");
    }
}
