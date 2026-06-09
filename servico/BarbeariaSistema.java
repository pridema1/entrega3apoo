package br.com.cortecerto.servico;

import br.com.cortecerto.modelo.Agendamento;
import br.com.cortecerto.modelo.Atendente;
import br.com.cortecerto.modelo.Atendimento;
import br.com.cortecerto.modelo.Barbeiro;
import br.com.cortecerto.modelo.Cliente;
import br.com.cortecerto.modelo.FormaPagamento;
import br.com.cortecerto.modelo.Pagamento;
import br.com.cortecerto.modelo.Proprietario;
import br.com.cortecerto.modelo.Relatorio;
import br.com.cortecerto.modelo.Servico;
import br.com.cortecerto.modelo.StatusAgendamento;
import br.com.cortecerto.modelo.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BarbeariaSistema {
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private List<Barbeiro> barbeiros = new ArrayList<Barbeiro>();
    private List<Servico> servicos = new ArrayList<Servico>();
    private List<Agendamento> agendamentos = new ArrayList<Agendamento>();
    private List<Atendimento> atendimentos = new ArrayList<Atendimento>();
    private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void cadastrarBarbeiro(Barbeiro barbeiro) {
        barbeiros.add(barbeiro);
    }

    public void cadastrarServico(Servico servico) {
        servicos.add(servico);
    }

    // Figura 3 - Sequencia UC01 Agendar Horario:
    // buscarCliente -> consultarServico -> verificarDisponibilidade -> criar/salvarAgendamento.
    public Agendamento agendarHorario(Atendente atendente, Cliente cliente, Barbeiro barbeiro, Servico servico, LocalDateTime dataHora) {
        if (!clientes.contains(cliente)) {
            throw new IllegalArgumentException("RN01: cliente deve estar previamente cadastrado.");
        }
        if (!barbeiros.contains(barbeiro)) {
            throw new IllegalArgumentException("Barbeiro deve estar cadastrado.");
        }
        if (!servicos.contains(servico)) {
            throw new IllegalArgumentException("Servico deve estar cadastrado.");
        }
        if (!validarDisponibilidade(barbeiro, dataHora, servico)) {
            throw new IllegalArgumentException("RN02/RN03: horario ocupado para duracao do servico.");
        }

        Agendamento agendamento = new Agendamento(cliente, barbeiro, servico, dataHora, "");
        agendamentos.add(agendamento);
        return agendamento;
    }

    // RN02 e RN03: evita dois atendimentos no mesmo intervalo, considerando duracao.
    public boolean validarDisponibilidade(Barbeiro barbeiro, LocalDateTime inicioNovo, Servico servicoNovo) {
        LocalDateTime fimNovo = inicioNovo.plusMinutes(servicoNovo.calcularDuracao());

        for (Agendamento existente : agendamentos) {
            if (!existente.getBarbeiro().equals(barbeiro)) {
                continue;
            }
            if (existente.getStatus() == StatusAgendamento.CANCELADO) {
                continue;
            }

            LocalDateTime inicioExistente = existente.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(existente.getServico().calcularDuracao());
            boolean temConflito = inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente);

            if (temConflito) {
                return false;
            }
        }
        return true;
    }

    public List<Agendamento> consultarAgenda(LocalDate data, Barbeiro barbeiro) {
        List<Agendamento> resultado = new ArrayList<Agendamento>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getBarbeiro().equals(barbeiro) && agendamento.getDataHora().toLocalDate().equals(data)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    // Caso de uso 2 - Registrar Atendimento: Barbeiro escolhe agendamento ativo e sistema atualiza historico.
    public Atendimento registrarAtendimento(Barbeiro barbeiro, Agendamento agendamento, String observacoes) {
        if (!agendamento.getBarbeiro().equals(barbeiro)) {
            throw new IllegalArgumentException("Barbeiro deve ser o responsavel pelo agendamento.");
        }
        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new IllegalStateException("Fluxo alternativo 2a: agendamento cancelado nao gera atendimento.");
        }

        agendamento.iniciarAtendimento();
        agendamento.concluirAtendimento();
        agendamento.enviarParaPagamento();

        Atendimento atendimento = new Atendimento(agendamento, observacoes);
        atendimentos.add(atendimento);
        return atendimento;
    }

    // RN04: somente Atendente ou Proprietario registra pagamento.
    public Pagamento registrarPagamento(Usuario usuario, Atendimento atendimento, FormaPagamento formaPagamento) {
        if (!(usuario instanceof Atendente) && !(usuario instanceof Proprietario)) {
            throw new IllegalArgumentException("RN04: apenas Atendente ou Proprietario podem registrar pagamento.");
        }

        Pagamento pagamento = new Pagamento(atendimento, formaPagamento);
        pagamento.confirmar();
        pagamentos.add(pagamento);
        return pagamento;
    }

    public Relatorio emitirRelatorio(Proprietario proprietario, LocalDate inicio, LocalDate fim) {
        List<Atendimento> atendimentosPeriodo = new ArrayList<Atendimento>();
        List<Pagamento> pagamentosPeriodo = new ArrayList<Pagamento>();

        for (Atendimento atendimento : atendimentos) {
            LocalDate data = atendimento.getDataRealizacao().toLocalDate();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                atendimentosPeriodo.add(atendimento);
            }
        }

        for (Pagamento pagamento : pagamentos) {
            LocalDate data = pagamento.getDataPagamento().toLocalDate();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                pagamentosPeriodo.add(pagamento);
            }
        }

        return new Relatorio(inicio, fim, atendimentosPeriodo, pagamentosPeriodo);
    }
}
