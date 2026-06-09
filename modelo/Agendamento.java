package br.com.cortecerto.modelo;

import java.time.LocalDateTime;

public class Agendamento {
    private Cliente cliente;
    private Barbeiro barbeiro;
    private Servico servico;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String observacoes;

    // Figura 2 - Agendamento concentra associacoes: 1 cliente, 1 barbeiro, 1 servico.
    public Agendamento(Cliente cliente, Barbeiro barbeiro, Servico servico, LocalDateTime dataHora, String observacoes) {
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.servico = servico;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
        this.status = StatusAgendamento.PENDENTE;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    // Figura 4 - Transicao pendente -> confirmado.
    public void confirmar() {
        if (status != StatusAgendamento.PENDENTE) {
            throw new IllegalStateException("Apenas agendamento pendente pode ser confirmado.");
        }
        status = StatusAgendamento.CONFIRMADO;
    }

    // Figura 4 - Transicao pendente/confirmado -> cancelado.
    public void cancelar() {
        if (status == StatusAgendamento.PAGO || status == StatusAgendamento.CONCLUIDO) {
            throw new IllegalStateException("Agendamento concluido ou pago nao pode ser cancelado.");
        }
        status = StatusAgendamento.CANCELADO;
    }

    public void iniciarAtendimento() {
        if (status != StatusAgendamento.CONFIRMADO) {
            throw new IllegalStateException("Somente agendamento confirmado pode iniciar atendimento.");
        }
        status = StatusAgendamento.EM_ATENDIMENTO;
    }

    public void concluirAtendimento() {
        if (status != StatusAgendamento.EM_ATENDIMENTO) {
            throw new IllegalStateException("Somente atendimento em andamento pode ser concluido.");
        }
        status = StatusAgendamento.CONCLUIDO;
    }

    public void enviarParaPagamento() {
        if (status != StatusAgendamento.CONCLUIDO) {
            throw new IllegalStateException("Somente atendimento concluido vai para pagamento.");
        }
        status = StatusAgendamento.AGUARDANDO_PAGAMENTO;
    }

    public void marcarComoPago() {
        if (status != StatusAgendamento.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Somente agendamento aguardando pagamento pode ser pago.");
        }
        status = StatusAgendamento.PAGO;
    }
}
