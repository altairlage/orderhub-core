package br.com.orderhub.core.domain.presenters;

import java.util.ArrayList;
import java.util.List;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.EstoqueDTO;

public class EstoquePresenter {

    public static EstoqueDTO toDTO(Estoque estoque) {
        return new EstoqueDTO(
                estoque.getSku(),
                estoque.getQuantidadeDisponivel()
        );
    }

    public static Estoque toDomain(EstoqueDTO dto) {
        return Estoque.builder()
                .sku(dto.sku())
                .quantidadeDisponivel(dto.quantidadeDisponivel())
                .build();
    }

    public static List<EstoqueDTO> toListDTO(List<Estoque> estoques) {
        List<EstoqueDTO> lista = new ArrayList<>();
        for (Estoque estoque : estoques) {
            lista.add(toDTO(estoque));
        }
        return lista;
    }
}
