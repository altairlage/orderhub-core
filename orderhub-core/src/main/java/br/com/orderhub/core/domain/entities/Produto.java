package br.com.orderhub.core.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;

    public Produto(Long id, String nome, String descricao, Double preco) {
        setId(id);
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
    }

    public Produto(String nome, String descricao, Double preco) {
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
    }

    public void setId(Long id) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("ID vazio ou inválido");
        }
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
        }
        this.descricao = descricao.trim();
    }

    public void setPreco(Double preco) {
        if (preco == null || preco < 0) {
            throw new IllegalArgumentException("O preço deve ser um valor positivo.");
        }
        this.preco = preco;
    }


}
