package br.com.orderhub.core.dto.pedidos;

import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.pagamentos.PagamentoDTO;

import java.util.List;
import java.util.Map;

public record PedidoDTO(Long idPedido, ClienteDTO cliente, PagamentoDTO pagamento, List<Map<String, Object>> listaQtdProdutos, StatusPedido status) {
}
