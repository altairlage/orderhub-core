package br.com.orderhub.core.interfaces;

import java.util.Optional;

import br.com.orderhub.core.domain.entities.Estoque;

public interface IEstoqueGateway {

    Optional<Estoque> buscarPorSku(String sku);

    void salvar(Estoque estoque);
}
