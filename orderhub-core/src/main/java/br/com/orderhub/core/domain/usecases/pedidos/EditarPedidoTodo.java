package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.exceptions.PedidoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IPedidoGateway;

public class EditarPedidoTodo {
    private final IPedidoGateway pedidoGateway;


    public EditarPedidoTodo(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido run(PedidoDTO pedidoAtualizado) throws PedidoNaoEncontradoException {
        Pedido pedidoAntigo = pedidoGateway.buscarPorId(pedidoAtualizado.idPedido());
        if (pedidoAntigo == null) {
            throw new PedidoNaoEncontradoException("Pedido com ID " + pedidoAtualizado.idPedido() + " nao foi encontrado");
        }
        return pedidoGateway.atualizarTudo(pedidoAntigo, PedidoPresenter.ToDomain(pedidoAtualizado));
    }
}
