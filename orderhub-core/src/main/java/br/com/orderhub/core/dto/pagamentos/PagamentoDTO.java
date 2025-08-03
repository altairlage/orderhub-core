package br.com.orderhub.core.dto.pagamentos;

import br.com.orderhub.core.domain.enums.StatusPagamento;

public record PagamentoDTO(Long id, String nomeCliente, String emailCliente, Double valorTotalOrdemPagamento, StatusPagamento status) {
}
