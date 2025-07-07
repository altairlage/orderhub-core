package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.exceptions.PedidoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IPedidoGateway;

public class EditarPedidoStatus {
    private final IPedidoGateway pedidoGateway;

    public EditarPedidoStatus(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido run(Long idPedido, StatusPedido status) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidoGateway.buscarPorId(idPedido);
        if(pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido com ID " + pedido.getIdPedido() + " nao foi encontrado");
        }
        return pedidoGateway.atualizarStatus(idPedido, status);
    }
}
