package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.exceptions.OrdemPagamentoNaoEncontradaException;
import br.com.orderhub.core.interfaces.IPagamentoGateway;

public class FecharOrdemPagamento {
    private final IPagamentoGateway gateway;

    public FecharOrdemPagamento(IPagamentoGateway gateway) {
        this.gateway = gateway;
    }

    public Pagamento run(Long idOrdemPagamento, StatusPagamento status) {
        if (status == null || !status.toString().contains("FECHADO")){
            throw new IllegalArgumentException("Status nulo ou invalido");
        }

        Pagamento pagamento = gateway.buscarOrderPagamentoPorId(idOrdemPagamento);

        if (pagamento == null){
            throw new OrdemPagamentoNaoEncontradaException("Ordem de pagamento com idProduto " + idOrdemPagamento + " não encontrada");
        }

        return gateway.fecharOrdemPagamento(idOrdemPagamento, status);
    }
}
