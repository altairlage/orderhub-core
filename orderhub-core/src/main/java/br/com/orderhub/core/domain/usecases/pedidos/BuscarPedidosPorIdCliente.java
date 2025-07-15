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
    private final IClienteGateway clienteGateway;

    public BuscarPedidosPorIdCliente(IPedidoGateway pedidoGateway, IClienteGateway clienteGateway) {
        this.pedidoGateway = pedidoGateway;
        this.clienteGateway = clienteGateway;
    }

    public List<Pedido> run(Long idCliente) throws PedidoNaoEncontradoException {
        Cliente cliente = clienteGateway.buscarPorId(idCliente);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + idCliente + " nao foi encontrado");
        }
        return pedidoGateway.buscarPorIdCliente(idCliente);
    }
}
