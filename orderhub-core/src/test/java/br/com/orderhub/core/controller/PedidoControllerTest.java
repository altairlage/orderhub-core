package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.domain.presenters.PagamentoPresenter;
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
    private Map<String, Object> mapCriarProduto1 = new HashMap<>();
    private Map<String, Object> mapCriarProduto2 = new HashMap<>();
    private Map<String, Object> mapProduto1 = new HashMap<>();
    private Map<String, Object> mapProduto2 = new HashMap<>();
    private ClienteDTO clienteDTO;
    private Cliente clienteCriado;
    private Pedido pedidoCriado1;
    private Pedido pedidoCriado2;
    private CriarPedidoDTO criarPedidoDTO;
    private Pagamento pagamentoCriado1;
    private Pagamento pagamentoCriado2;

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

        mapCriarProduto1.put("quantidade", 2);
        mapCriarProduto1.put("idProduto", 1L);
        mapCriarProduto2.put("quantidade", 1);
        mapCriarProduto2.put("idProduto", 2L);

        mapProduto1.put("quantidade", 2);
        mapProduto1.put("produto", produtoCriado1);
        mapProduto2.put("quantidade", 1);
        mapProduto2.put("produto", produtoCriado2);

        clienteDTO = new ClienteDTO(
                1L,
                "Adamastor",
                "123.456.789-09",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        clienteCriado = new Cliente(
                1L,
                "Adamastor",
                "123.456.789-09",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        pagamentoCriado1 = new Pagamento(1L, StatusPagamento.EM_ABERTO);
        pagamentoCriado2 = new Pagamento(2L, StatusPagamento.EM_ABERTO);

        criarPedidoDTO = new CriarPedidoDTO(1L, Arrays.asList(mapCriarProduto1, mapCriarProduto2), StatusPedido.ABERTO);

        pedidoCriado1 = new Pedido(1L, clienteCriado, pagamentoCriado1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        pedidoCriado2 = new Pedido(2L, clienteCriado, pagamentoCriado2, Arrays.asList(mapProduto2, mapProduto1), StatusPedido.ABERTO);
    }

    @Test
    public void deveCriarPedidoComSucesso(){
        when(clienteGateway.buscarPorId(any(Long.class))).thenReturn(clienteCriado);
        when(produtoGateway.buscarPorId(1L)).thenReturn(produtoCriado1);
        when(produtoGateway.buscarPorId(2L)).thenReturn(produtoCriado2);

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
        Pedido pedidoAtualizado = new Pedido(1L, clienteCriado, pagamentoCriado1, Arrays.asList(mapProduto2, mapProduto1), StatusPedido.FECHADO_PELO_CLIENTE);

        when(pedidoGateway.buscarPorId(any(Long.class))).thenReturn(pedidoCriado1);
        when(pedidoGateway.editar(any(Pedido.class), any(Pedido.class))).thenReturn(pedidoAtualizado);

        PedidoDTO resultado = controller.editarPedido(PedidoPresenter.ToDTO(pedidoAtualizado));

        assertNotNull(resultado);
        assertEquals(StatusPedido.FECHADO_PELO_CLIENTE, resultado.status());
        assertEquals(PagamentoPresenter.ToDTO(pagamentoCriado1), resultado.pagamento());
        verify(pedidoGateway).editar(pedidoCriado1, pedidoAtualizado);
    }

    @Test
    public void deveEditarPedidoStatusComSucesso(){
        Pedido pedidoAtualizado = new Pedido(1L, clienteCriado, pagamentoCriado2, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.FECHADO_SUCESSO);

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
