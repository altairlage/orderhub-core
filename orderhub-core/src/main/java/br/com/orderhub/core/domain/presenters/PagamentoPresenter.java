package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.dto.pagamentos.PagamentoDTO;

public class PagamentoPresenter {
    public static PagamentoDTO ToDTO(Pagamento pagamento) {
        return new PagamentoDTO(pagamento.getId(), pagamento.getStatus());
    }

    public static Pagamento ToDomain(PagamentoDTO pagamentoDTO){
        return new Pagamento(pagamentoDTO.id(), pagamentoDTO.status());
    }
}
