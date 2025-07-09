package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class BuscarProdutoPorNome {
    private final IProdutoGateway gateway;

    public BuscarProdutoPorNome(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Produto run(String nome) throws ProdutoNaoEncontradoException {
        Produto produto = gateway.buscarPorNome(nome);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto com nome " + nome + "não encontrado");
        }
        return produto;
    }
}
