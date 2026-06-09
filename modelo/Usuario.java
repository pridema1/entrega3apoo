package br.com.cortecerto.modelo;

public abstract class Usuario {
    private String nome;
    private String email;
    private String senhaHash;
    private String perfil;

    // Figura 2 - Classe Usuario: superclasse de Atendente, Barbeiro e Proprietario.
    public Usuario(String nome, String email, String senhaHash, String perfil) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String getPerfil() {
        return perfil;
    }

    public boolean autenticar(String email, String senhaHash) {
        return this.email.equals(email) && this.senhaHash.equals(senhaHash);
    }

    public void acessarSistema() {
        System.out.println(nome + " acessou o sistema como " + perfil);
    }

    // Polimorfismo: cada tipo de usuario explica suas permissoes de forma propria.
    public abstract String resumoPermissao();
}
