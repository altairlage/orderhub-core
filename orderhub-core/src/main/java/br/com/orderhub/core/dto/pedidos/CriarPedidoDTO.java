package br.com.orderhub.core.dto.pedidos;

import br.com.orderhub.core.domain.enums.StatusPedido;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record CriarPedidoDTO(Long idCliente, List<Map<String, Object>> listaQtdProdutos, StatusPedido status) implements Serializable {
}
