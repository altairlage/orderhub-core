package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class BuscarProdutoPorId {
    private final IProdutoGateway gateway;

    public BuscarProdutoPorId(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Produto run(Long id) throws ProdutoNaoEncontradoException {
        Produto produto = gateway.buscarPorId(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + "não encontrado");
        }
        return produto;
    }
}
