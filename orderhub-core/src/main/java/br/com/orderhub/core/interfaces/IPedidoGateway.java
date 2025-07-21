package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.enums.StatusPedido;

import java.util.List;

public interface IPedidoGateway {
    Pedido buscarPorId(Long idPedido);
    List<Pedido> buscarPorIdCliente(Long idCliente);
    Pedido criar(Pedido pedido);
    Pedido editar(Pedido pedidoAntigo, Pedido pedidoAtualizado);
    Pedido editarStatus(Long idPedido, StatusPedido status);
    List<Pedido> listarTodos();
}
