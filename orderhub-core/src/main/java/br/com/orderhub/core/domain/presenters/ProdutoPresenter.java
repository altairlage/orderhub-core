package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;

public class ProdutoPresenter {

    public static ProdutoDTO ToDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco()
        );
    }

    public static Produto ToDomain(ProdutoDTO produtoDTO){
        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.descricao(),
                produtoDTO.preco()
        );
    }
}
