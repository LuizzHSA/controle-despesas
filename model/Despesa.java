package model;

import interfaces.Pagavel;

public abstract class Despesa implements Pagavel {
    protected String descricao;
    protected double valor;
    protected String dataVencimento;
    protected String tipo;
    protected boolean paga;

    public Despesa(String descricao, double valor, String dataVencimento, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = false;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isPaga() {
        return paga;
    }

    @Override
    public void registrarPagamento(double valor, String dataPagamento) {
        this.paga = true;
        System.out.println("Pagamento de R$ " + valor + " registrado para " + descricao + " em " + dataPagamento);
    }

    public String getStatus() {
        return paga ? "PAGA" : "EM ABERTO";
    }

    public String toFileString() {
        // descricao;valor;dataVencimento;tipo;paga
        return descricao + ";" + valor + ";" + dataVencimento + ";" + tipo + ";" + paga;
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + descricao + " | R$" + valor + " | Venc: " + dataVencimento + " | " + getStatus();
    }
}
