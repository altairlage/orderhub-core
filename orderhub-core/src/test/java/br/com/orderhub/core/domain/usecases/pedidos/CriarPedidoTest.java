package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CriarPedidoTest {
    private CriarPedido criarPedido;
    private IPedidoGateway pedidoGateway;
    private IClienteGateway clienteGateway;
    private IProdutoGateway produtoGateway;

    // Variáveis muito utilizadas
    private CriarProdutoDTO produtoDTO1;
    private CriarProdutoDTO produtoDTO2;
    private Produto produtoCriado1;
    private Produto produtoCriado2;
    private Map<Integer, CriarProdutoDTO> mapCriarProduto1 = new HashMap<>();
    private Map<Integer, CriarProdutoDTO> mapCriarProduto2 = new HashMap<>();
    private Map<Integer, Produto> mapProduto1 = new HashMap<>();
    private Map<Integer, Produto> mapProduto2 = new HashMap<>();
    private ClienteDTO clienteDTO;
    private Cliente clienteCriado;
    private Pedido pedidoCriado1;
    private Pedido pedidoCriado2;
    private CriarPedidoDTO criarPedidoDTO;

    @BeforeEach
    public void setUp() {
        pedidoGateway = mock(IPedidoGateway.class);
        clienteGateway = mock(IClienteGateway.class);
        produtoGateway = mock(IProdutoGateway.class);
        criarPedido = new CriarPedido(pedidoGateway, clienteGateway, produtoGateway);

        produtoDTO1 = new CriarProdutoDTO("Arroz", "Branco", 20.0);
        produtoDTO2 = new CriarProdutoDTO("Feijão", "Preto", 20.0);

        produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        produtoCriado2 = new Produto("Feijão", "Preto", 20.0);

        mapCriarProduto1.put(2, produtoDTO1);
        mapCriarProduto2.put(3, produtoDTO2);

        mapProduto1.put(2, produtoCriado1);
        mapProduto2.put(3, produtoCriado2);

        clienteDTO = new ClienteDTO(
                1L,
                "Adamastor",
                "123.456.789-10",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        clienteCriado = new Cliente(
                1L,
                "Adamastor",
                "123.456.789-10",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        criarPedidoDTO = new CriarPedidoDTO(clienteDTO, 1L, Arrays.asList(mapCriarProduto1, mapCriarProduto2), StatusPedido.ABERTO);

        pedidoCriado1 = new Pedido(1L, clienteCriado, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
    }

    @Test
    public void deveCriarPedidoComSucesso(){
        when(clienteGateway.buscarPorCpf(any(String.class))).thenReturn(clienteCriado);
        when(produtoGateway.buscarPorNome(any(String.class))).thenReturn(produtoCriado1);
        when(pedidoGateway.criar(any(Pedido.class))).thenReturn(pedidoCriado1);

        Pedido resultado = criarPedido.run(criarPedidoDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getCliente().getId());
    }

    @Test
    public void deveLancarExcecaoQuandoClienteNaoExiste(){
        when(clienteGateway.buscarPorCpf(any(String.class))).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> criarPedido.run(criarPedidoDTO));
    }
}
