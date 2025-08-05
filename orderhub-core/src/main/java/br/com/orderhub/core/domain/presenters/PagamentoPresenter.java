package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.dto.pagamentos.PagamentoDTO;

public class PagamentoPresenter {
    public static PagamentoDTO ToDTO(Pagamento pagamento) {
        return new PagamentoDTO(pagamento.getId(), pagamento.getIdPedido(), pagamento.getNomeCliente(), pagamento.getEmailCliente(), pagamento.getValorTotalOrdemPagamento(), pagamento.getStatus());
    }

    public static Pagamento ToDomain(PagamentoDTO pagamentoDTO){
        return new Pagamento(pagamentoDTO.id(), pagamentoDTO.idPedido(), pagamentoDTO.nomeCliente(), pagamentoDTO.emailCliente(), pagamentoDTO.valorTotalOrdemPagamento(), pagamentoDTO.status());
    }
}
