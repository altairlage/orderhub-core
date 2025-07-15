package br.com.orderhub.core.dto.pedidos;

import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;

import java.util.List;
import java.util.Map;

public record PedidoDTO(Long idPedido, ClienteDTO cliente, Long idPagamento, List<Map<Integer, ProdutoDTO>> listaQtdProdutos, StatusPedido status) {
}
