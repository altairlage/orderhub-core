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
                PagamentoPresenter.ToDTO(pedido.getPagamento()),
                ToProductListDTO(pedido.getListaQtdProdutos()),
                pedido.getStatus());
    }

    public static Pedido ToDomain(PedidoDTO pedidoDTO) {
        return new Pedido(
                pedidoDTO.idPedido(),
                ClientePresenter.ToDomain(pedidoDTO.cliente()),
                PagamentoPresenter.ToDomain(pedidoDTO.pagamento()),
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

    public static List<Map<String, Object>> ToProductListDTO(List<Map<String, Object>> produtosLista) {
        List<Map<String, Object>> listaRespostaDTO = new ArrayList<>();

        for (Map<String, Object> map : produtosLista) {
            Integer quantidade = null;
            Produto produto = null;

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("quantidade")) {
                    quantidade = entry.getValue() == null ? 0 : Integer.parseInt(entry.getValue().toString());
                }

                if (entry.getKey().equals("produto")) {
                    produto = (Produto) entry.getValue();
                }
            }

            ProdutoDTO produtoDTO = ProdutoPresenter.ToDTO(produto);
            Map<String, Object> mapProdutoDTO = new HashMap<>();
            mapProdutoDTO.put("quantidade", quantidade);
            mapProdutoDTO.put("produto", produtoDTO);

            listaRespostaDTO.add(mapProdutoDTO);
        }
        return listaRespostaDTO;
    }

    public static List<Map<String, Object>> ToProductListDomain(List<Map<String, Object>> produtosDTOLista) {
        List<Map<String, Object>> listaRespostaDomain = new ArrayList<>();

        for (Map<String, Object> map : produtosDTOLista) {
            Integer quantidade = null;
            ProdutoDTO produtoDTO = null;

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("quantidade")) {
                    quantidade = Integer.parseInt(entry.getValue().toString());
                }

                if (entry.getKey().equals("produto")) {
                    produtoDTO = (ProdutoDTO) entry.getValue();
                }
            }

            Produto produto = ProdutoPresenter.ToDomain(produtoDTO);
            Map<String, Object> item = new HashMap<>();
            item.put("quantidade", quantidade);
            item.put("produto", produto);

            listaRespostaDomain.add(item);
        }
        return listaRespostaDomain;
    }
}
