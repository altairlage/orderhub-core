package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import br.com.orderhub.core.interfaces.IProdutoGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriarPedido {
    private final IPedidoGateway pedidoGateway;


    public CriarPedido(IPedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido run(CriarPedidoDTO criarPedidoDTO) {
        final Pedido pedido = new Pedido(
                criarPedidoDTO.idCliente(),
                criarPedidoDTO.listaQtdProdutos(),
                criarPedidoDTO.status()
        );

        return pedidoGateway.criar(pedido);

    }
}
