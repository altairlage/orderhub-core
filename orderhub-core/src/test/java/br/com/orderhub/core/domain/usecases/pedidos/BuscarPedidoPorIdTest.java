package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.exceptions.PedidoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarPedidoPorIdTest {
    private IPedidoGateway pedidoGateway;
    private BuscarPedidoPorId buscarPedidoPorId;

    @BeforeEach
    public void setUp(){
        pedidoGateway = mock(IPedidoGateway.class);
        buscarPedidoPorId = new BuscarPedidoPorId(pedidoGateway);
    }

    @Test
    public void deveBuscarPorIdComSucesso(){
        Produto produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        Produto produtoCriado2 = new Produto("Feijão", "Preto", 20.0);
        Map<Integer, Produto> mapProduto1 = Map.of(2, produtoCriado1);
        Map<Integer, Produto> mapProduto2 = Map.of(3, produtoCriado2);

        Cliente cliente = new Cliente(1L,
                "Jorge",
                "123.456.789.10",
                "07/12/2015",
                "",
                "(99) 99999-9999",
                "email@email.com",
                "");

        Pedido pedido = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        when(pedidoGateway.buscarPorId(1L)).thenReturn(pedido);

        Pedido resultado = buscarPedidoPorId.run(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPedido());
        assertEquals("Jorge", resultado.getCliente().getNome());
        assertEquals(StatusPedido.ABERTO, resultado.getStatus());
        verify(pedidoGateway).buscarPorId(1L);
    }

    @Test
    public void deveLancarExcecaoQuandoPedidoNaoExiste(){
        when(pedidoGateway.buscarPorId(2L)).thenReturn(null);

        assertThrows(PedidoNaoEncontradoException.class, () -> buscarPedidoPorId.run(2L));
        verify(pedidoGateway).buscarPorId(2L);
    }
}
