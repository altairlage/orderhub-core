package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.interfaces.IPagamentoGateway;

public class GerarOrdemPagamento {
    private final IPagamentoGateway gateway;

    public GerarOrdemPagamento(IPagamentoGateway gateway) {
        this.gateway = gateway;
    }

    public Pagamento run() {
        try{
            return gateway.gerarOrdemPagamento();
        } catch (Exception e){
            throw new RuntimeException("Erro ao gerar ordem de pagamento", e);
        }
    }
}
