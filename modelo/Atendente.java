package br.com.cortecerto.modelo;

public class Atendente extends Usuario {
    private int ramal;

    // Figura 1 - Atendente executa agendar horario e registrar pagamento.
    public Atendente(String nome, String email, String senhaHash, String perfil, int ramal) {
        super(nome, email, senhaHash, perfil);
        this.ramal = ramal;
    }

    public int getRamal() {
        return ramal;
    }

    @Override
    public String resumoPermissao() {
        return "Atendente: agenda horarios, mantem clientes e registra pagamentos.";
    }
}
