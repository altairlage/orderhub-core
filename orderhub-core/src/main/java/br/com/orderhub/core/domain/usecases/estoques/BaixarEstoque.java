package br.com.orderhub.core.domain.usecases.estoques;

import java.util.function.Supplier;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class BaixarEstoque {

    private final IEstoqueGateway estoqueGateway;

    public BaixarEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public void executar(String sku, int quantidade) {
        Supplier<EstoqueNaoEncontradoException> exception =
                () -> new EstoqueNaoEncontradoException("Estoque não encontrado para o SKU: " + sku);

        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(exception);

        if (estoque.getQuantidadeDisponivel() < quantidade) {
            throw new EstoqueInsuficienteException(
                String.format("Estoque insuficiente para SKU: %s. Solicitado: %d, Disponível: %d",
                    sku, quantidade, estoque.getQuantidadeDisponivel())
            );
        }

        estoque.baixarEstoque(quantidade);
        estoqueGateway.salvar(estoque);
    }
}
