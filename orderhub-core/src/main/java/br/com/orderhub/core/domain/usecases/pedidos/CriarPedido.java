package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.interfaces.IPedidoGateway;

public class CriarPedido {
    private final IPedidoGateway pedidoGateway;


    public CriarPedido(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido run(CriarPedidoDTO criarPedidoDTO) {
        final Pedido pedido = new Pedido(
                criarPedidoDTO.idCliente(),
                criarPedidoDTO.listaQtdProdutos(),
                criarPedidoDTO.status()
        );

        return pedidoGateway.criar(pedido);

    }
}
