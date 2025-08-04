package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.exceptions.PedidoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;

import java.util.List;

public class BuscarPedidosPorIdCliente {
    private final IPedidoGateway pedidoGateway;

    public BuscarPedidosPorIdCliente(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public List<Pedido> run(Long idCliente) throws PedidoNaoEncontradoException {
        return pedidoGateway.buscarPorIdCliente(idCliente);
    }
}
