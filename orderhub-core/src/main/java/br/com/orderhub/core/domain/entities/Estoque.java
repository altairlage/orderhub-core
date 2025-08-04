package br.com.orderhub.core.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Estoque {

    private Long idProduto;
    private Integer quantidadeDisponivel;

    public Estoque(Long idProduto, Integer quantidadeDisponivel) {
        setIdProduto(idProduto);
        setQuantidadeDisponivel(quantidadeDisponivel);
    }

    public void setIdProduto(Long idProduto) {
        if (idProduto == null || idProduto <= 0) {
            throw new IllegalArgumentException("ID de Produto nulo ou inválido");
        }
        this.idProduto = idProduto;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        if (quantidadeDisponivel == null) {
            throw new IllegalArgumentException("Quantidade disponível para produto nulo");
        }
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public void baixarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para baixa deve ser maior que zero.");
        }

        if (this.quantidadeDisponivel < quantidade) {
            throw new IllegalStateException("Estoque insuficiente para a operação.");
        }

        this.quantidadeDisponivel -= quantidade;
    }

    public void reporEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para reposição deve ser maior que zero.");
        }

        this.quantidadeDisponivel += quantidade;
    }
}
