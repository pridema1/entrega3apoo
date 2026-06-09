package br.com.cortecerto;

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
import br.com.cortecerto.modelo.Usuario;
import br.com.cortecerto.servico.BarbeariaSistema;

import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        BarbeariaSistema sistema = new BarbeariaSistema();

        // Figura 1 - Casos de uso: Atendente, Barbeiro e Proprietario sao atores do sistema.
        Atendente atendente = new Atendente("Ana Caixa", "ana@cortecerto.com", "hash123", "ATENDENTE", 2211);
        Barbeiro barbeiro = new Barbeiro("Carlos Navalha", "carlos@cortecerto.com", "hash456", "BARBEIRO", "Cortes masculinos");
        Proprietario proprietario = new Proprietario("Joao Dono", "joao@cortecerto.com", "hash789", "PROPRIETARIO", "12.345.678/0001-90");

        Cliente cliente = new Cliente("Pedro Silva", "(44) 99999-0000", "pedro@email.com");
        Servico corte = new Servico("Corte degrade", "Corte masculino com acabamento", 45.00, 45);

        // Polimorfismo: a lista usa Usuario, mas cada objeto executa seu proprio resumoPermissao().
        List<Usuario> usuarios = Arrays.asList(atendente, barbeiro, proprietario);
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.resumoPermissao());
        }

        sistema.cadastrarCliente(cliente);
        sistema.cadastrarBarbeiro(barbeiro);
        sistema.cadastrarServico(corte);

        // Figura 2 - Diagrama de classes: Cliente, Barbeiro e Servico se ligam em Agendamento.
        // Figura 3 - Sequencia UC01: tela solicita dados, controller valida disponibilidade e salva.
        Agendamento agendamento = sistema.agendarHorario(
                atendente,
                cliente,
                barbeiro,
                corte,
                LocalDateTime.of(2026, 6, 3, 15, 0)
        );
        agendamento.confirmar();

        // Figura 4 - Estados: confirmado -> em atendimento -> concluido -> aguardando pagamento.
        Atendimento atendimento = sistema.registrarAtendimento(
                barbeiro,
                agendamento,
                "Cliente pediu acabamento mais baixo nas laterais."
        );

        // RN04: apenas Atendente ou Proprietario registra pagamento.
        Pagamento pagamento = sistema.registrarPagamento(atendente, atendimento, FormaPagamento.PIX);
        System.out.println("Pagamento confirmado? " + pagamento.isConfirmado());

        List<Agendamento> agendaDoBarbeiro = sistema.consultarAgenda(LocalDate.of(2026, 6, 3), barbeiro);
        System.out.println("Agenda do barbeiro: " + agendaDoBarbeiro.size() + " agendamento(s)");

        // Figura 2 - Relatorio e gerado pelo Proprietario e usa atendimentos/pagamentos concluidos.
        Relatorio relatorio = sistema.emitirRelatorio(
                proprietario,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30)
        );

        System.out.println("Atendimentos no relatorio: " + relatorio.getTotalAtendimentos());
        System.out.println("Faturamento: R$ " + relatorio.calcularFaturamento());
    }
}
