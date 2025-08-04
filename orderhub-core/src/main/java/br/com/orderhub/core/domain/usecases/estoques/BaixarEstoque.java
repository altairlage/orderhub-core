package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class BaixarEstoque {

    private final IEstoqueGateway estoqueGateway;

    public BaixarEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public Estoque run(Estoque estoqueDTO) {
        if (estoqueDTO.getQuantidadeDisponivel() <= 0) {
            throw new IllegalArgumentException("A quantidade solicitada para baixar do estoque deve ser maior que zero.");
        }

        Estoque produtoEmEstoque = estoqueGateway.consultarEstoquePorIdProduto(estoqueDTO.getIdProduto());

        if (produtoEmEstoque == null) {
            throw new EstoqueNaoEncontradoException("Produto de ID " + estoqueDTO.getIdProduto() + " não encontrado no estoque");
        }


        if (produtoEmEstoque.getQuantidadeDisponivel() < estoqueDTO.getQuantidadeDisponivel()) {
            throw new EstoqueInsuficienteException("Quantidade insuficiente de produto ID " + produtoEmEstoque.getIdProduto() + " no estoque");
        }

        return estoqueGateway.baixarEstoque(estoqueDTO);
    }
}