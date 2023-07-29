package org.desafio;

import java.util.List;

public class Pessoa {

    private String nome;
    private int idade;
    private String cpf;
    private List<Endereco> enderecos;


    public Pessoa(String nome, int idade, String cpf, List<Endereco> enderecos) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.enderecos = enderecos;
    }

    public Pessoa(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
