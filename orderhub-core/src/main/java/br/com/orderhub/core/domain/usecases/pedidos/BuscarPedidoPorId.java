package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.exceptions.PedidoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IPedidoGateway;

public class BuscarPedidoPorId {
    private final IPedidoGateway gateway;


    public BuscarPedidoPorId(IPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public Pedido run(Long id) throws PedidoNaoEncontradoException {
        Pedido pedido = gateway.buscarPorId(id);
        if (pedido == null) {
            throw new  PedidoNaoEncontradoException("Pedido com ID " + id + " não encontrado");
        }
        return pedido;
    }
}
