package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class ConsultarEstoquePorIdProduto {

    private final IEstoqueGateway estoqueGateway;

    public ConsultarEstoquePorIdProduto(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public Estoque run(Long idProduto) {
        Estoque response = estoqueGateway.consultarEstoquePorIdProduto(idProduto);

        if (response == null) {
            throw new EstoqueNaoEncontradoException("Produto de ID " + idProduto + " não encontrado no estoque");
        }

        return response;
    }
}