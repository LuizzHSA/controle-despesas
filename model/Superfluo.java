package model;

public class Superfluo extends Despesa {
    public Superfluo(String descricao, double valor, String dataVencimento) {
        super(descricao, valor, dataVencimento, "Superfluo");
    }

    @Override
    public void registrarPagamento(double valor, String dataPagamento) {
        super.registrarPagamento(valor, dataPagamento);
        System.out.println("🛍️ Despesa supérflua quitada.");
    }
}
