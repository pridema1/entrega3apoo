package br.com.cortecerto.modelo;

public class Proprietario extends Usuario {
    private String cnpj;

    // Figura 1 - Proprietario emite relatorios e acompanha faturamento.
    public Proprietario(String nome, String email, String senhaHash, String perfil, String cnpj) {
        super(nome, email, senhaHash, perfil);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String resumoPermissao() {
        return "Proprietario: acompanha agenda, emite relatorios e pode registrar pagamentos.";
    }
}
