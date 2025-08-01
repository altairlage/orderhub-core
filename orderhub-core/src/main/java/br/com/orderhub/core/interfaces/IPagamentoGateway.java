package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.dto.clientes.ClienteDTO;

public interface IPagamentoGateway {
    Pagamento gerarOrdemPagamento(ClienteDTO clienteDTO) throws Exception;
    Pagamento fecharOrdemPagamento(Long idOrdemPagamento, StatusPagamento statusPagamento);
    Pagamento buscarOrderPagamentoPorId(Long id);
}
