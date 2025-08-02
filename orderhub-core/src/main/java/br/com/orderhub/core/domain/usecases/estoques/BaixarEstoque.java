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

    // MUDANÇA: Assinatura do método agora usa Long id
    public void executar(Long id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade solicitada deve ser maior que zero.");
        }

        // MUDANÇA: Mensagem de erro atualizada para ID
        Supplier<EstoqueNaoEncontradoException> exception =
                () -> new EstoqueNaoEncontradoException("Estoque não encontrado para o ID: " + id);

        // MUDANÇA: Chama o novo método do gateway
        Estoque estoque = estoqueGateway.buscarPorId(id)
                .orElseThrow(exception);

        if (estoque.getQuantidadeDisponivel() < quantidade) {
            // MUDANÇA: Mensagem de erro atualizada para ID
            throw new EstoqueInsuficienteException(
                String.format("Estoque insuficiente para o ID: %d. Solicitado: %d, Disponível: %d",
                    id, quantidade, estoque.getQuantidadeDisponivel())
            );
        }

        estoque.baixarEstoque(quantidade);
        estoqueGateway.salvar(estoque);
    }
}