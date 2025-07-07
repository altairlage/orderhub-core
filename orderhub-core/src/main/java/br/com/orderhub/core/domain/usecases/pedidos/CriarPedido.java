package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.interfaces.IPedidoGateway;

public class CriarPedido {
    private final IPedidoGateway pedidoGateway;


    public CriarPedido(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido run(PedidoDTO pedidoDTO) {
        final Pedido pedido = PedidoPresenter.ToDomain(pedidoDTO);
        return pedidoGateway.criar(pedido);
    }
}
