package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;
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
    private final IClienteGateway clienteGateway;
    private final IProdutoGateway produtoGateway;


    public CriarPedido(IPedidoGateway pedidoGateway, IClienteGateway clienteGateway, IProdutoGateway produtoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.clienteGateway = clienteGateway;
        this.produtoGateway = produtoGateway;
    }

    public Pedido run(CriarPedidoDTO pedidoDTO) {
        final Cliente cliente = clienteGateway.buscarPorCpf(pedidoDTO.clienteDTO().email());
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com CPF: " + pedidoDTO.clienteDTO().cpf() + " não encontrado");
        }

        List<Map<Integer, Produto>> listaProdutos = new ArrayList<>();

        for(Map<Integer, CriarProdutoDTO> produtoDTOMap : pedidoDTO.listaQtdProdutos()){
            for (Map.Entry<Integer, CriarProdutoDTO> entry : produtoDTOMap.entrySet()) {
                Integer quantidade = entry.getKey();
                Produto produto = produtoGateway.buscarPorNome(entry.getValue().nome());
                if (produto == null) {
                    throw new ProdutoNaoEncontradoException("Produto com nome: " + entry.getValue().nome() + " não encontrado");
                }
                Map<Integer, Produto> mapProduto = new HashMap<>();
                mapProduto.put(quantidade, produto);
                listaProdutos.add(mapProduto);
            }

        }

        final Pedido pedido = new Pedido(
                ClientePresenter.ToDomain(pedidoDTO.clienteDTO()),
                pedidoDTO.idPagamento(),
                listaProdutos,
                pedidoDTO.Status()
        );

        return pedidoGateway.criar(pedido);

    }
}
