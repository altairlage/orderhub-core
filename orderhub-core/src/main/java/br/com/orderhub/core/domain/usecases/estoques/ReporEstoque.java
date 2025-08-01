package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class ReporEstoque {

    private final IEstoqueGateway estoqueGateway;

    public ReporEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public void executar(Long id, int quantidade) {
        Estoque estoque = estoqueGateway.buscarPorId(id)
            .orElseThrow(() -> new EstoqueNaoEncontradoException(
                "Estoque não encontrado para o ID: " + id
            ));

        estoque.reporEstoque(quantidade);
        estoqueGateway.salvar(estoque);
    }
}