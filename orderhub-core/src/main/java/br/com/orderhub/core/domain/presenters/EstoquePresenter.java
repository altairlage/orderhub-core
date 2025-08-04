package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.AtualizarEstoqueDTO;
import br.com.orderhub.core.dto.estoques.EstoqueDTO;

public class EstoquePresenter {

    public static EstoqueDTO toDTO(Estoque estoque) {
        return new EstoqueDTO(
                estoque.getIdProduto(),
                estoque.getQuantidadeDisponivel()
        );
    }

    public static Estoque toDomain(EstoqueDTO dto) {
        return new Estoque(
                dto.idProduto(),
                dto.quantidadeDisponivel()
        );
    }

    public static Estoque atualizarDtoToDomain(AtualizarEstoqueDTO estoqueDTO){
        return new Estoque(
                estoqueDTO.idProduto(),
                estoqueDTO.quantidade()
        );
    }
}
