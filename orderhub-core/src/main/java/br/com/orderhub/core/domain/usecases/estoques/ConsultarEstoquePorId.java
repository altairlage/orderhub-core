package br.com.orderhub.core.domain.usecases.estoques;

import java.util.Optional;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class ConsultarEstoquePorId {

    private final IEstoqueGateway estoqueGateway;

    public ConsultarEstoquePorId(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public Optional<Estoque> run(Long id) {
        return estoqueGateway.buscarPorId(id);
    }
}