package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.exceptions.PedidoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IPedidoGateway;

public class EditarPedido {
    private final IPedidoGateway pedidoGateway;

    public EditarPedido(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido run(PedidoDTO pedidoAtualizado) throws PedidoNaoEncontradoException {
        Pedido pedidoAntigo = pedidoGateway.buscarPorId(pedidoAtualizado.idPedido());
        if (pedidoAntigo == null) {
            throw new PedidoNaoEncontradoException("Pedido com ID " + pedidoAtualizado.idPedido() + " nao foi encontrado");
        }
        return pedidoGateway.editar(pedidoAntigo, PedidoPresenter.ToDomain(pedidoAtualizado));
    }
}
