package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.domain.presenters.PagamentoPresenter;
import br.com.orderhub.core.domain.usecases.pagamentos.BuscarPagamentoPorId;
import br.com.orderhub.core.domain.usecases.pagamentos.FecharOrdemPagamento;
import br.com.orderhub.core.domain.usecases.pagamentos.GerarOrdemPagamento;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.pagamentos.PagamentoDTO;
import br.com.orderhub.core.interfaces.IPagamentoGateway;

public class PagamentoController {
    private final IPagamentoGateway gateway;
    private final BuscarPagamentoPorId buscarPagamentoPorId;
    private final FecharOrdemPagamento fecharOrdemPagamento;
    private final GerarOrdemPagamento gerarOrdemPagamento;

    public PagamentoController(IPagamentoGateway gateway) {
        this.gateway = gateway;
        this.buscarPagamentoPorId = new BuscarPagamentoPorId(gateway);
        this.fecharOrdemPagamento = new FecharOrdemPagamento(gateway);
        this.gerarOrdemPagamento = new GerarOrdemPagamento(gateway);
    }

    public PagamentoDTO buscarPagamentoPorId(Long idOrdemPagamento){
        return PagamentoPresenter.ToDTO(buscarPagamentoPorId.run(idOrdemPagamento));
    }

    public PagamentoDTO fecharOrdemPagamento(Long idOrdemPagamento, StatusPagamento status){
        return PagamentoPresenter.ToDTO(fecharOrdemPagamento.run(idOrdemPagamento, status));
    }

    public PagamentoDTO gerarOrdemPagamento(ClienteDTO clienteDTO){
        return PagamentoPresenter.ToDTO(gerarOrdemPagamento.run(clienteDTO));
    }
}
