package br.com.orderhub.core.domain.usecases;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.presenters.ProdutoPresenter;
import br.com.orderhub.core.dto.ProdutoDTO;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class EditarProduto {
    private final IProdutoGateway gateway;

    public EditarProduto(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public Produto run(ProdutoDTO produtoDTO) {
        Produto produto = gateway.buscarPorId(produtoDTO.id());
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto com ID " + produtoDTO.id() + "não encontrado");
        }

        if (produtoDTO.nome() != null) {
            produto.setNome(produtoDTO.nome());
        }
        if (produtoDTO.descricao() != null) {
            produto.setDescricao(produtoDTO.descricao());
        }
        if (produtoDTO.preco() != null) {
            produto.setPreco(produtoDTO.preco());
        }

        Produto produtoAtualizado = gateway.atualizar(produto);
        return produtoAtualizado;
    }
}
