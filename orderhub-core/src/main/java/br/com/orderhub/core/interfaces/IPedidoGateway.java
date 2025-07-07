package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.enums.StatusPedido;

import java.util.List;

public interface IPedidoGateway {
    Pedido buscarPorId(Long idPedido);
    List<Pedido> buscarPorIdCliente(Long idCliente);
    Pedido criar(Pedido pedido);
    Pedido atualizarTudo(Pedido pedidoAntigo, Pedido pedidoAtualizado);
    Pedido atualizarStatus(Long idPedido,StatusPedido status);
}
