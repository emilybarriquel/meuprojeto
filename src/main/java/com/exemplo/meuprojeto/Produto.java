package com.exemplo.meuprojeto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Produto {

    /// ///////bean validation:
    ///
    @NotNull(message = "O ID não pode ser nulo")
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "O preço não pode ser nulo")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    private Double preco;

    @NotNull(message = "O estoque não pode ser nulo")
    @Min(value = 0, message = "O estoque deve ser maior ou igual a zero")
    private Integer estoque;

    // Construtor
    public Produto(Long id, String nome, Double preco, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
}
