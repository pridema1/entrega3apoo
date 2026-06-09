package br.com.cortecerto.modelo;

import java.time.LocalDateTime;

public class Pagamento {
    private Atendimento atendimento;
    private double valor;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataPagamento;
    private boolean confirmado;

    // Figura 2 - Atendimento gera 0..1 Pagamento.
    public Pagamento(Atendimento atendimento, FormaPagamento formaPagamento) {
        this.atendimento = atendimento;
        this.valor = atendimento.getAgendamento().getServico().getPreco();
        this.formaPagamento = formaPagamento;
        this.dataPagamento = LocalDateTime.now();
        this.confirmado = false;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public double getValor() {
        return valor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void confirmar() {
        confirmado = true;
        atendimento.getAgendamento().marcarComoPago();
    }
}
