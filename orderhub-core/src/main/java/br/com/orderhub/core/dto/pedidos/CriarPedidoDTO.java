package br.com.orderhub.core.dto.pedidos;

import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;

import java.util.List;
import java.util.Map;

public record CriarPedidoDTO(Long idCliente, Long idPagamento, List<Map<Integer, ProdutoDTO>> listaQtdProdutos, StatusPedido Status) {
}
