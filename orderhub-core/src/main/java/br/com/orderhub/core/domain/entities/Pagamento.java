package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.interfaces.InputStringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Pagamento {
    private Long id;
    private Long idPedido;
    private String nomeCliente;
    private String emailCliente;
    private Double valorTotalOrdemPagamento;
    private StatusPagamento status;

    public Pagamento(Long id, Long idPedido, String nomeCliente, String emailCliente, Double valorTotalOrdemPagamento, StatusPagamento status) {
        setId(id);
        setIdPedido(idPedido);
        setNomeCliente(nomeCliente);
        setEmailCliente(emailCliente);
        setValorTotalOrdemPagamento(valorTotalOrdemPagamento);
        setStatus(status);
    }

    public Pagamento(Long idPedido, String nomeCliente, String emailCliente, Double valorTotalOrdemPagamento, StatusPagamento status) {
        setId(idPedido);
        setNomeCliente(nomeCliente);
        setEmailCliente(emailCliente);
        setValorTotalOrdemPagamento(valorTotalOrdemPagamento);
        setStatus(status);
    }

    public void setStatus(StatusPagamento status) {
        if (this.status == StatusPagamento.EM_ABERTO && !status.toString().contains("FECHADO")) {
            throw new IllegalArgumentException("Ordem de Pagamento com status 'ABERTO' só pode ser fechada");
        }

        this.status = status;
    }

    public void setId(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("ID inválido ou nulo");
        }
        this.id = id;
    }

    public void setIdPedido(Long idPedido) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("ID de pedido inválido ou nulo");
        }
        this.idPedido = idPedido;
    }

    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente nulo ou inválido");
        }
        this.nomeCliente = nomeCliente;
    }

    public void setEmailCliente(String emailCliente) {
        if (emailCliente == null || emailCliente.isEmpty() || !InputStringValidator.isValidEmail(emailCliente)) {
            throw new IllegalArgumentException("E-mail do cliente nulo ou inválido");
        }
        this.emailCliente = emailCliente;
    }

    public void setValorTotalOrdemPagamento(Double valorTotalOrdemPagamento) {
        if (valorTotalOrdemPagamento == null || valorTotalOrdemPagamento < 0.0) {
            throw new IllegalArgumentException("Valor da ordem pagamento nulo ou inválido");
        }
        this.valorTotalOrdemPagamento = valorTotalOrdemPagamento;
    }
}
