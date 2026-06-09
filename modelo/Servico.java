package br.com.cortecerto.modelo;

public class Servico {
    private String nome;
    private String descricao;
    private double preco;
    private int duracaoMinutos;

    // Figura 2 - Servico e incluido em 0..* agendamentos.
    public Servico(String nome, String descricao, double preco, int duracaoMinutos) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int calcularDuracao() {
        return duracaoMinutos;
    }
}
