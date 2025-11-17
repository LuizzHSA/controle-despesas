package model;

public class Alimentacao extends Despesa {
    public Alimentacao(String descricao, double valor, String dataVencimento) {
        super(descricao, valor, dataVencimento, "Alimentacao");
    }

    @Override
    public void registrarPagamento(double valor, String dataPagamento) {
        super.registrarPagamento(valor, dataPagamento);
        System.out.println("🍽️ Despesa de alimentação quitada!");
    }
}
