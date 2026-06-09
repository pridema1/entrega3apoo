package br.com.cortecerto.modelo;

// Figura 4 - Diagrama de estados da entidade Consulta/Agendamento.
public enum StatusAgendamento {
    PENDENTE,
    CONFIRMADO,
    EM_ATENDIMENTO,
    CONCLUIDO,
    AGUARDANDO_PAGAMENTO,
    PAGO,
    CANCELADO
}
