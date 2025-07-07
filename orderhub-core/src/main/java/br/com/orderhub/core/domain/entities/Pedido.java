package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class Pedido {
    Long idPedido;
    Long idCliente;
    Long idPagamento;
    List<Map<Integer, ProdutoDTO>> listaQtdProdutos;
    StatusPedido status;

    public Pedido(Long idPedido, Long idCliente, Long idPagamento, List<Map<Integer, ProdutoDTO>> listaQtdProdutos, StatusPedido status) {
        setIdPedido(idPedido);
        setIdCliente(idCliente);
        setIdPagamento(idPagamento);
        setListaQtdProdutos(listaQtdProdutos);
        setStatus(status);
    }

    public void setIdPedido(Long idPedido) {
        if (idPedido != null && idPedido <= 0) {
            throw new IllegalArgumentException("ID do pedido está vazio ou é inválido");
        }
        this.idPedido = idPedido;
    }

    public void setIdCliente(Long idCliente) {
        if (idCliente != null && idCliente <= 0) {
            throw new IllegalArgumentException("ID do cliente está vazio ou é inválido");
        }
        this.idCliente = idCliente;
    }

    public void setIdPagamento(Long idPagamento) {
        if (idPagamento != null && idPagamento <= 0) {
            throw new IllegalArgumentException("ID do pagamento está vazio ou é inválido");
        }
        this.idPagamento = idPagamento;
    }
}
