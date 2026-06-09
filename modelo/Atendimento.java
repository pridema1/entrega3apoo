package br.com.cortecerto.modelo;

import java.time.LocalDateTime;

public class Atendimento {
    private Agendamento agendamento;
    private LocalDateTime dataRealizacao;
    private String observacoes;
    private StatusAgendamento status;

    // Figura 2 - Agendamento gera 0..1 Atendimento.
    public Atendimento(Agendamento agendamento, String observacoes) {
        this.agendamento = agendamento;
        this.dataRealizacao = LocalDateTime.now();
        this.observacoes = observacoes;
        this.status = StatusAgendamento.CONCLUIDO;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public StatusAgendamento getStatus() {
        return status;
    }
}
