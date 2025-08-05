package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.interfaces.IPedidoGateway;
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
    private PedidoController controller;

    // Variáveis muito utilizadas
    private Produto produtoCriado1;
    private Produto produtoCriado2;
    private Map<String, Object> mapCriarProduto1 = new HashMap<>();
    private Map<String, Object> mapCriarProduto2 = new HashMap<>();
    private Map<String, Object> mapProduto1 = new HashMap<>();
    private Map<String, Object> mapProduto2 = new HashMap<>();
    private Long idCliente = 1L;
    private Long idPagamento1 = 1L;
    private Long idPagamento2 = 2L;
    private Pedido pedidoCriado1;
    private Pedido pedidoCriado2;
    private CriarPedidoDTO criarPedidoDTO;


    @BeforeEach
    public void setUp() {
        pedidoGateway = mock(IPedidoGateway.class);
        controller = new PedidoController(pedidoGateway);

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

        criarPedidoDTO = new CriarPedidoDTO(1L, Arrays.asList(mapCriarProduto1, mapCriarProduto2), StatusPedido.ABERTO);

        pedidoCriado1 = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        pedidoCriado2 = new Pedido(2L, idCliente, idPagamento2, Arrays.asList(mapProduto2, mapProduto1), StatusPedido.ABERTO);
    }

    @Test
    public void deveCriarPedidoComSucesso(){

        when(pedidoGateway.criar(any(Pedido.class))).thenReturn(pedidoCriado1);

        PedidoDTO resultado = controller.criarPedido(criarPedidoDTO);

        assertNotNull(resultado);
        assertEquals(StatusPedido.ABERTO, resultado.status());
        assertEquals(idCliente, resultado.idCliente());
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
        when(pedidoGateway.buscarPorIdCliente(anyLong())).thenReturn(Arrays.asList(pedidoCriado1, pedidoCriado2));

        List<PedidoDTO> resultado = controller.buscarPedidosPorIdCliente(1L);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(pedidoGateway).buscarPorIdCliente(1L);
    }

    @Test
    public void deveEditarPedidoComSucesso(){
        Pedido pedidoAtualizado = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto2, mapProduto1), StatusPedido.FECHADO_SEM_CREDITO);

        when(pedidoGateway.buscarPorId(any(Long.class))).thenReturn(pedidoCriado1);
        when(pedidoGateway.editar(any(Pedido.class), any(Pedido.class))).thenReturn(pedidoAtualizado);

        PedidoDTO resultado = controller.editarPedido(PedidoPresenter.ToDTO(pedidoAtualizado));

        assertNotNull(resultado);
        assertEquals(StatusPedido.FECHADO_SEM_CREDITO, resultado.status());
        assertEquals(idPagamento1, resultado.idPagamento());
        verify(pedidoGateway).editar(pedidoCriado1, pedidoAtualizado);
    }

    @Test
    public void deveEditarPedidoStatusComSucesso(){
        Pedido pedidoAtualizado = new Pedido(1L, idCliente, idPagamento2, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.FECHADO_SUCESSO);

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
