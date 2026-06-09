package br.com.cortecerto.modelo;

public class Barbeiro extends Usuario {
    private String especialidade;

    // Figura 1 - Barbeiro consulta agenda e registra atendimento.
    public Barbeiro(String nome, String email, String senhaHash, String perfil, String especialidade) {
        super(nome, email, senhaHash, perfil);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String resumoPermissao() {
        return "Barbeiro: consulta agenda e registra atendimentos realizados.";
    }
}
