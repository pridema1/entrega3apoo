package br.com.cortecerto.modelo;

import java.time.LocalDate;
import java.util.List;

public class Relatorio {
    private LocalDate periodoInicial;
    private LocalDate periodoFinal;
    private List<Atendimento> atendimentos;
    private List<Pagamento> pagamentos;

    // Figura 2 - Proprietario gera Relatorio; RN05 remove cancelados/nao realizados do faturamento.
    public Relatorio(LocalDate periodoInicial, LocalDate periodoFinal, List<Atendimento> atendimentos, List<Pagamento> pagamentos) {
        this.periodoInicial = periodoInicial;
        this.periodoFinal = periodoFinal;
        this.atendimentos = atendimentos;
        this.pagamentos = pagamentos;
    }

    public LocalDate getPeriodoInicial() {
        return periodoInicial;
    }

    public LocalDate getPeriodoFinal() {
        return periodoFinal;
    }

    public int getTotalAtendimentos() {
        return atendimentos.size();
    }

    public double calcularFaturamento() {
        double total = 0.0;
        for (Pagamento pagamento : pagamentos) {
            if (pagamento.isConfirmado()) {
                total += pagamento.getValor();
            }
        }
        return total;
    }
}
