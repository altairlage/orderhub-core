package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class RemoverProdutoNoEstoque {
    private final IEstoqueGateway estoqueGateway;

    public RemoverProdutoNoEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public void run(Long idProduto) {
        Estoque verificacao = estoqueGateway.consultarEstoquePorIdProduto(idProduto);

        if (verificacao == null) {
            throw new EstoqueNaoEncontradoException("Produto de ID " + idProduto + " não encontrado no estoque");
        }

        estoqueGateway.removerProdutoNoEstoque(idProduto);
    }
}