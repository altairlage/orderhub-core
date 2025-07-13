package br.com.orderhub.core.dto.pedidos;

import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;

import java.util.List;
import java.util.Map;

public record CriarPedidoDTO(ClienteDTO clienteDTO, Long idPagamento, List<Map<Integer, CriarProdutoDTO>> listaQtdProdutos, StatusPedido Status) {
}
