package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class ReporEstoque {

    private final IEstoqueGateway estoqueGateway;

    public ReporEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public void executar(String sku, int quantidade) {
        Estoque estoque = estoqueGateway.buscarPorSku(sku)
            .orElseThrow(() -> new EstoqueNaoEncontradoException(sku));

        estoque.reporEstoque(quantidade);
        estoqueGateway.salvar(estoque);
    }
}
