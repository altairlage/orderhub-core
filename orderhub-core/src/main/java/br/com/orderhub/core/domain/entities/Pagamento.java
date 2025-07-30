package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPagamento;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Pagamento {
    private Long id;
    private StatusPagamento status;

    public Pagamento(Long id, StatusPagamento status) {
        this.id = id;
        setStatus(status);
    }

    public Pagamento(StatusPagamento status) {
        setStatus(status);
    }

    public void setStatus(StatusPagamento status) {
        if (this.status == StatusPagamento.ABERTO && !status.toString().contains("FECHADO")) {
            throw new IllegalArgumentException("Ordem de Pagamento com status 'ABERTO' só pode ser fechada");
        }

        this.status = status;
    }
}
