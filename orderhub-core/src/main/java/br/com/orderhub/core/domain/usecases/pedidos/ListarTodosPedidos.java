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
        List<Pedido> listaResposta = new ArrayList<>();
        List<Pedido> pedidos = gateway.listarTodos();
        for (Pedido pedido : pedidos){
            if (pedido.getStatus().equals(StatusPedido.ABERTO)) listaResposta.add(pedido);
        }
        return listaResposta;
    }
}
