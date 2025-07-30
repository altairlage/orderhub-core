package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;

public interface IPagamentoGateway {
    Pagamento gerarOrdemPagamento();
    Pagamento fecharOrdemPagamento(Long idOrdemPagamento, StatusPagamento statusPagamento);
    Pagamento buscarOrderPagamentoPorId(Long id);
}
