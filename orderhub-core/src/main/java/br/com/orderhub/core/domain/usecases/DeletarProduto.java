package br.com.orderhub.core.domain.usecases;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class DeletarProduto {
    private final IProdutoGateway gateway;

    public DeletarProduto(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public void run(Long id) {
        Produto produto = gateway.buscarPorId(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + "não encontrado");
        }
        gateway.deletar(id);
    }
}
