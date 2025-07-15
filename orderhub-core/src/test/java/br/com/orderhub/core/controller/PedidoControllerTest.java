package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoControllerTest {

    private IPedidoGateway pedidoGateway;
    private IProdutoGateway produtoGateway;
    private IClienteGateway clienteGateway;
    private PedidoController controller;

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
        produtoGateway = mock(IProdutoGateway.class);
        clienteGateway = mock(IClienteGateway.class);
        controller = new PedidoController(pedidoGateway, clienteGateway, produtoGateway);

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
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        clienteCriado = new Cliente(
                1L,
                "Adamastor",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        criarPedidoDTO = new CriarPedidoDTO(clienteDTO, 1L, Arrays.asList(mapCriarProduto1, mapCriarProduto1), StatusPedido.ABERTO);

        pedidoCriado1 = new Pedido(1L, clienteCriado, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        pedidoCriado2 = new Pedido(2L, clienteCriado, 2L, Arrays.asList(mapProduto2, mapProduto1), StatusPedido.ABERTO);
    }

    @Test
    public void deveCriarPedidoComSucesso(){
        when(clienteGateway.buscarPorEmail("email@email.com")).thenReturn(clienteCriado);
        when(produtoGateway.buscarPorNome("Arroz")).thenReturn(produtoCriado1);
        when(produtoGateway.buscarPorNome("Feijão")).thenReturn(produtoCriado2);

        when(pedidoGateway.criar(any(Pedido.class))).thenReturn(pedidoCriado1);

        PedidoDTO resultado = controller.criarPedido(criarPedidoDTO);

        assertNotNull(resultado);
        assertEquals(StatusPedido.ABERTO, resultado.status());
        assertEquals("Adamastor", resultado.cliente().nome());
        verify(pedidoGateway).criar(any(Pedido.class));
    }

    @Test
    public void deveBuscarPorIdComSucesso(){
        when(pedidoGateway.buscarPorId(any(Long.class))).thenReturn(pedidoCriado1);

        PedidoDTO resultado = controller.buscarPedidoPorId(1L);

        assertNotNull(resultado);
        verify(pedidoGateway).buscarPorId(1L);
    }

    @Test
    public void deveBuscarPedidosPorIdClienteComSucesso(){
        when(clienteGateway.buscarPorId(any(Long.class))).thenReturn(clienteCriado);
        when(pedidoGateway.buscarPorIdCliente(anyLong())).thenReturn(Arrays.asList(pedidoCriado1, pedidoCriado2));

        List<PedidoDTO> resultado = controller.buscarPedidosPorIdCliente(1L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(pedidoGateway).buscarPorIdCliente(1L);
    }

    @Test
    public void deveEditarPedidoComSucesso(){
        Pedido pedidoAtualizado = new Pedido(1L, clienteCriado, 3L, Arrays.asList(mapProduto2, mapProduto1), StatusPedido.FECHADO_PELO_CLIENTE);

        when(pedidoGateway.buscarPorId(any(Long.class))).thenReturn(pedidoCriado1);
        when(pedidoGateway.editar(any(Pedido.class), any(Pedido.class))).thenReturn(pedidoAtualizado);

        PedidoDTO resultado = controller.editarPedido(PedidoPresenter.ToDTO(pedidoAtualizado));

        assertNotNull(resultado);
        assertEquals(StatusPedido.FECHADO_PELO_CLIENTE, resultado.status());
        assertEquals(3L, resultado.idPagamento());
        verify(pedidoGateway).editar(pedidoCriado1, pedidoAtualizado);
    }

    @Test
    public void deveEditarPedidoStatusComSucesso(){
        Pedido pedidoAtualizado = new Pedido(1L, clienteCriado, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.FECHADO_SUCESSO);

        when(pedidoGateway.buscarPorId(any(Long.class))).thenReturn(pedidoCriado1);
        when(pedidoGateway.editarStatus(any(Long.class), any(StatusPedido.class))).thenReturn(pedidoAtualizado);

        PedidoDTO resultado = controller.editarPedidoStatus(1L, StatusPedido.FECHADO_SUCESSO);

        assertNotNull(resultado);
        assertEquals(StatusPedido.FECHADO_SUCESSO, resultado.status());
        verify(pedidoGateway).editarStatus(1L, StatusPedido.FECHADO_SUCESSO);

    }

    @Test
    public void deveListarPedidosComSucesso(){
        when(pedidoGateway.listarTodos()).thenReturn(Arrays.asList(pedidoCriado2, pedidoCriado2));

        List<PedidoDTO> resultado = controller.listarTodosPedidos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(StatusPedido.ABERTO, resultado.get(0).status());
        assertEquals(StatusPedido.ABERTO, resultado.get(1).status());
    }
}
