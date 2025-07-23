package br.com.orderhub.core.domain.usecases.estoques;

import java.util.Optional;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class ConsultarEstoquePorSku {

    private final IEstoqueGateway estoqueGateway;

    public ConsultarEstoquePorSku(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public Optional<Estoque> run(String sku) {
        return estoqueGateway.buscarPorSku(sku);
    }
}
