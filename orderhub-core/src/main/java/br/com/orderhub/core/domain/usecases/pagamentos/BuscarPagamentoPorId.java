package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.exceptions.OrdemPagamentoNaoEncontradaException;
import br.com.orderhub.core.interfaces.IPagamentoGateway;

public class BuscarPagamentoPorId {
    private final IPagamentoGateway gateway;

    public BuscarPagamentoPorId(IPagamentoGateway gateway) {
        this.gateway = gateway;
    }

    public Pagamento run(Long idOrdemPagamento){
        Pagamento pagamento = gateway.buscarOrderPagamentoPorId(idOrdemPagamento);

        if(pagamento == null){
            throw new OrdemPagamentoNaoEncontradaException("Ordem de pagamento com idProduto " + idOrdemPagamento + " não encontrada");
        }

        return pagamento;
    }
}
