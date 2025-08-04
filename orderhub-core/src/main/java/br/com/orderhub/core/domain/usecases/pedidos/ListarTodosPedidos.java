package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.interfaces.IPedidoGateway;

import java.util.ArrayList;
import java.util.List;

public class ListarTodosPedidos {
    private final IPedidoGateway gateway;

    public ListarTodosPedidos(IPedidoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Pedido> run(){
        return gateway.listarTodos();
    }
}
