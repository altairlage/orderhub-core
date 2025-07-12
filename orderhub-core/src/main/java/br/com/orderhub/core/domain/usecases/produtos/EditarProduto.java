package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class EditarProduto {
    private final IProdutoGateway gateway;

    public EditarProduto(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public br.com.orderhub.core.domain.entities.Produto run(ProdutoDTO produtoDTO) {
        br.com.orderhub.core.domain.entities.Produto produto = gateway.buscarPorId(produtoDTO.id());
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

        return gateway.atualizar(produto);
    }
}
