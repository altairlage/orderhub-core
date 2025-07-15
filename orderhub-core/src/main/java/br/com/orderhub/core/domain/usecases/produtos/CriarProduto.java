package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;
import br.com.orderhub.core.exceptions.ProdutoJaExisteException;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class CriarProduto {
    private final IProdutoGateway gateway;

    public CriarProduto(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Produto run(CriarProdutoDTO produtoDTO) {
        final Produto produtoExistente = gateway.buscarPorNome(produtoDTO.nome());

        if (produtoExistente != null) {
            throw new ProdutoJaExisteException("O produto " + produtoDTO.nome() + "Já existe!");
        }
        final Produto novoProduto = new Produto(produtoDTO.nome(), produtoDTO.descricao(), produtoDTO.preco());
        return gateway.criar(novoProduto);
    }

}
