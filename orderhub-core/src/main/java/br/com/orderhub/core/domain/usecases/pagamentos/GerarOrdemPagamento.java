package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.dto.pagamentos.CriarPagamentoDTO;
import br.com.orderhub.core.exceptions.PagamentoErroGeracaoException;
import br.com.orderhub.core.interfaces.IPagamentoGateway;

public class GerarOrdemPagamento {
    private final IPagamentoGateway gateway;

    public GerarOrdemPagamento(IPagamentoGateway gateway) {
        this.gateway = gateway;
    }

    public Pagamento run(CriarPagamentoDTO criarPagamentoDTO) throws Exception {
        try{
            Pagamento pagamentoDTO = new Pagamento(
                    criarPagamentoDTO.idPedido(),
                    criarPagamentoDTO.nomeCliente(),
                    criarPagamentoDTO.emailCliente(),
                    criarPagamentoDTO.valorTotalOrdemPagamento(),
                    criarPagamentoDTO.status());

            return gateway.gerarOrdemPagamento(pagamentoDTO);
        } catch (Exception e){
            throw new PagamentoErroGeracaoException("Erro ao gerar ordem de pagamento. Exception:" + e);
        }
    }
}
