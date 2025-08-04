package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPedido;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class Pedido {
    private Long idPedido;
    private Long idCliente;
    private Long idPagamento;
    private List<Map<String, Object>> listaQtdProdutos;
    private StatusPedido status = null;

    public Pedido(Long idPedido, Long idCliente, Long idPagamento, List<Map<String, Object>> listaQtdProdutos, StatusPedido status) {
        setIdPedido(idPedido);
        setIdCliente(idCliente);
        setIdPagamento(idPagamento);
        setListaQtdProdutos(listaQtdProdutos);
        setStatus(status);
    }

    public Pedido(Long idCliente, List<Map<String, Object>> listaQtdProdutos, StatusPedido status) {
        setIdCliente(idCliente);
        setListaQtdProdutos(listaQtdProdutos);
        setStatus(status);
    }

    public void setIdPedido(Long idPedido) {
        if (idPedido == null || idPedido <= 0) {
            throw new IllegalArgumentException("ID do pedido está vazio ou é inválido");
        }
        this.idPedido = idPedido;
    }

    public void setIdCliente(Long idCliente) {
        if (idCliente == null || idCliente <= 0) {
            throw new IllegalArgumentException("ID do cliente está vazio ou é inválido");
        }
        this.idCliente = idCliente;
    }

    public void setIdPagamento(Long idPagamento) {
        if (idPagamento == null || idPagamento <= 0) {
            throw new IllegalArgumentException("ID do pagamento está vazio ou é inválido");
        }
        this.idPagamento = idPagamento;
    }

    public void setListaQtdProdutos(List<Map<String, Object>> listaQtdProdutos) {
        if (listaQtdProdutos == null || listaQtdProdutos.isEmpty())
            throw new IllegalArgumentException("Lista de produtos do pedido está vazia ou é inválida");
        this.listaQtdProdutos = listaQtdProdutos;
    }

    public void setStatus(StatusPedido status) {
        if (status == null) throw new IllegalArgumentException("Status do pedido está vazio ou é inválido");

        if (this.status != null && this.status.equals(StatusPedido.ABERTO) && !status.toString().contains("FECHADO"))
            throw new IllegalArgumentException("Pedido com status 'ABERTO' só pode ser alterado para algum status 'FECHADO'");
        this.status = status;
    }
}
