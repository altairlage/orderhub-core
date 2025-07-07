package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;

import java.util.ArrayList;
import java.util.List;

public class PedidoPresenter {

    public static PedidoDTO ToDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getIdCliente(),
                pedido.getIdPagamento(),
                pedido.getListaQtdProdutos(),
                pedido.getStatus());
    }

    public static Pedido ToDomain(PedidoDTO pedidoDTO) {
        return new Pedido(
                pedidoDTO.idPedido(),
                pedidoDTO.idCliente(),
                pedidoDTO.idPagamento(),
                pedidoDTO.listaQtdProdutos(),
                pedidoDTO.status());
    }

    public static List<PedidoDTO> ToListDTO(List<Pedido> pedidosLista) {
        List<PedidoDTO> listaResposta = new ArrayList<>();
        for (Pedido pedido : pedidosLista) {
            listaResposta.add(ToDTO(pedido));
        }
        return listaResposta;
    }
}
