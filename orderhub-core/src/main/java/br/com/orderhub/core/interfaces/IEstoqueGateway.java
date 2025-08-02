package br.com.orderhub.core.interfaces;

import java.util.List; // Importe a classe List
import java.util.Optional;
import br.com.orderhub.core.domain.entities.Estoque;

public interface IEstoqueGateway {

    Optional<Estoque> buscarPorId(Long id);

    List<Estoque> buscarPorIds(List<Long> ids);

    void salvar(Estoque estoque);
}