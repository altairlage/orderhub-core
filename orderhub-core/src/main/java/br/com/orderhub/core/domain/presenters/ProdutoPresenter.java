package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ProdutoDTO> ToListDTO(List<Produto> produtosLista) {
        List<ProdutoDTO> listaResposta = new ArrayList<>();
        for (Produto produto : produtosLista) {
            listaResposta.add(ToDTO(produto));
        }
        return listaResposta;
    }
}
