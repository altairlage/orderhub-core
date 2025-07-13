package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoPresenter {

    public static PedidoDTO ToDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getIdPedido(),
                ClientePresenter.ToDTO(pedido.getCliente()),
                pedido.getIdPagamento(),
                ToProductListDTO(pedido.getListaQtdProdutos()),
                pedido.getStatus());
    }

    public static Pedido ToDomain(PedidoDTO pedidoDTO) {
        return new Pedido(
                pedidoDTO.idPedido(),
                ClientePresenter.ToDomain(pedidoDTO.cliente()),
                pedidoDTO.idPagamento(),
                ToProductListDomain(pedidoDTO.listaQtdProdutos()),
                pedidoDTO.status());
    }

    public static List<PedidoDTO> ToListDTO(List<Pedido> pedidosLista) {
        List<PedidoDTO> listaResposta = new ArrayList<>();

        for (Pedido pedido : pedidosLista) {
            listaResposta.add(ToDTO(pedido));
        }
        return listaResposta;
    }

    public static List<Map<Integer, ProdutoDTO>> ToProductListDTO(List<Map<Integer, Produto>> produtosLista) {
        List<Map<Integer, ProdutoDTO>> listaRespostaDTO = new ArrayList<>();

        for (Map<Integer, Produto> map : produtosLista) {
            for (Map.Entry<Integer, Produto> entry : map.entrySet()) {
                Integer quantidade = entry.getKey();
                Produto produto = entry.getValue();
                ProdutoDTO produtoDTO = ProdutoPresenter.ToDTO(produto);

                Map<Integer, ProdutoDTO> item = new HashMap<>();
                item.put(quantidade, produtoDTO);

                listaRespostaDTO.add(item);
            }
        }
        return listaRespostaDTO;
    }

    public static List<Map<Integer, Produto>> ToProductListDomain(List<Map<Integer, ProdutoDTO>> produtosDTOLista) {
        List<Map<Integer, Produto>> listaRespostaDomain = new ArrayList<>();

        for (Map<Integer, ProdutoDTO> map : produtosDTOLista) {
            for (Map.Entry<Integer, ProdutoDTO> entry : map.entrySet()) {
                Integer quantidade = entry.getKey();
                ProdutoDTO produtoDTO = entry.getValue();
                Produto produto = ProdutoPresenter.ToDomain(produtoDTO);

                Map<Integer, Produto> item = new HashMap<>();
                item.put(quantidade, produto);

                listaRespostaDomain.add(item);
            }
        }
        return listaRespostaDomain;
    }
}
