package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import br.com.orderhub.core.interfaces.IProdutoGateway;

import java.util.Map;

public class CriarPedido {
    private final IPedidoGateway pedidoGateway;
    private final IClienteGateway clienteGateway;
    private final IProdutoGateway produtoGateway;


    public CriarPedido(IPedidoGateway pedidoGateway, IClienteGateway clienteGateway, IProdutoGateway produtoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.clienteGateway = clienteGateway;
        this.produtoGateway = produtoGateway;
    }

    public Pedido run(PedidoDTO pedidoDTO) {
        final Cliente cliente = clienteGateway.buscarPorId(pedidoDTO.cliente().id());
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com ID: " + pedidoDTO.cliente().id() + " não encontrado");
        }

        for(Map<Integer, ProdutoDTO> produtoDTOMap : pedidoDTO.listaQtdProdutos()){
            for (Map.Entry<Integer, ProdutoDTO> entry : produtoDTOMap.entrySet()) {
                Produto produto = produtoGateway.buscarPorId(entry.getValue().id());
                if (produto == null) {
                    throw new ProdutoNaoEncontradoException("Produto com ID: " + entry.getValue().id() + " não encontrado");
                }
            }

        }
        final Pedido pedido = PedidoPresenter.ToDomain(pedidoDTO);
        return pedidoGateway.criar(pedido);

    }
}
