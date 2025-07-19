package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import java.util.function.Supplier;

public class BaixarEstoque {

    private final IEstoqueGateway estoqueGateway;

    public BaixarEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public void executar(String sku, int quantidade) {
        Supplier<EstoqueNaoEncontradoException> exception = () -> new EstoqueNaoEncontradoException(sku);

        Estoque estoque = estoqueGateway.buscarPorSku(sku)
                .orElseThrow(exception);

        if (estoque.getQuantidadeDisponivel() < quantidade) {
            throw new EstoqueInsuficienteException(sku, quantidade, estoque.getQuantidadeDisponivel());
        }

        estoque.baixarEstoque(quantidade);
        estoqueGateway.salvar(estoque);
    }
}
