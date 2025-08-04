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
    private final IClienteGateway clienteGateway;
    private final IProdutoGateway produtoGateway;


    public CriarPedido(IPedidoGateway pedidoGateway, IClienteGateway clienteGateway, IProdutoGateway produtoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.clienteGateway = clienteGateway;
        this.produtoGateway = produtoGateway;
    }

    public Pedido run(CriarPedidoDTO criarPedidoDTO) {
        final Cliente cliente = clienteGateway.buscarPorId(criarPedidoDTO.idCliente());
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }

        List<Map<String, Object>> listaProdutos = new ArrayList<>();

        for (Map<String, Object> produtoDTOMap : criarPedidoDTO.listaQtdProdutos()) {
            int quantidade = 0;
            Long idProduto;
            Produto produto = null;

            for (Map.Entry<String, Object> entry : produtoDTOMap.entrySet()) {
                if (entry.getKey().equals("quantidade")) {
                    try {
                        quantidade = entry.getValue() == null ? 0 : Integer.parseInt(entry.getValue().toString());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Quantidade inválida: " + entry.getKey());
                    }
                }

                if (entry.getKey().equals("idProduto")) {
                    try {
                        idProduto = Long.parseLong(entry.getValue().toString());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("ID de produto inválido: " + entry.getValue());
                    }

                    produto = produtoGateway.buscarPorId(idProduto);
                    if (produto == null) {
                        throw new ProdutoNaoEncontradoException("Produto com idProduto: " + idProduto + " não encontrado");
                    }
                }
                Map<String, Object> mapProduto = new HashMap<>();
                mapProduto.put("quantidade", quantidade);
                mapProduto.put("produto", produto);

                listaProdutos.add(mapProduto);
            }
        }

        final Pedido pedido = new Pedido(
                cliente,
                listaProdutos,
                criarPedidoDTO.status()
        );

        return pedidoGateway.criar(pedido);

    }
}
