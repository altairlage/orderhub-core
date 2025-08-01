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
    private Cliente cliente;
    private Pagamento pagamento;
    private List<Map<String, Object>> listaQtdProdutos;
    private StatusPedido status = null;

    public Pedido(Long idPedido, Cliente cliente, Pagamento pagamento, List<Map<String, Object>> listaQtdProdutos, StatusPedido status) {
        setIdPedido(idPedido);
        setCliente(cliente);
        setPagamento(pagamento);
        setListaQtdProdutos(listaQtdProdutos);
        setStatus(status);
    }

    public Pedido(Cliente cliente, List<Map<String, Object>> listaQtdProdutos, StatusPedido status) {
        setCliente(cliente);
        setListaQtdProdutos(listaQtdProdutos);
        setStatus(status);
    }

    public void setIdPedido(Long idPedido) {
        if (idPedido == null || idPedido <= 0) {
            throw new IllegalArgumentException("ID do pedido está vazio ou é inválido");
        }
        this.idPedido = idPedido;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null || cliente.getId() == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException("ID do cliente está vazio ou é inválido");
        }
        this.cliente = cliente;
    }

    public void setPagamento(Pagamento pagamento) {
        if (pagamento == null || pagamento.getId() == null || pagamento.getId() <= 0) {
            throw new IllegalArgumentException("ID do pagamento está vazio ou é inválido");
        }
        this.pagamento = pagamento;
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

    public Double calcularValorTotal() {
        double valorTotal = 0.0;
        for(Map<String, Object> produtoEQtdNoPedido: this.listaQtdProdutos){
            int quantidade = 0;
            Produto produto = new Produto();

            for(Map.Entry<String, Object> entry : produtoEQtdNoPedido.entrySet()){
                if (entry.getKey().equals("quantidade")) {
                    quantidade = Integer.parseInt(entry.getValue().toString());
                }
                if (entry.getKey().equals("produto")) {
                    produto = (Produto) entry.getValue();
                }
            }

            valorTotal += quantidade * produto.getPreco();
        }
        return valorTotal;
    }
}
