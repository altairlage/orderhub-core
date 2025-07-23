package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class BuscarPedidoPorIdClienteTest {
    private IPedidoGateway pedidoGateway;
    private IClienteGateway clienteGateway;
    private BuscarPedidosPorIdCliente buscarPedidosPorIdCliente;

    @BeforeEach
    public void setUp(){
        pedidoGateway = mock(IPedidoGateway.class);
        clienteGateway = mock(IClienteGateway.class);
        buscarPedidosPorIdCliente = new BuscarPedidosPorIdCliente(pedidoGateway, clienteGateway);
    }

    @Test
    public void deveBuscarPedidosPorIdClienteComSucesso(){
        Produto produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        Produto produtoCriado2 = new Produto("Feijão", "Preto", 20.0);
        Map<String, Object>  mapProduto1 = new HashMap<>();
        Map<String, Object> mapProduto2 = new HashMap<>();
        mapProduto1.put("quantidade", 1);
        mapProduto1.put("produto", produtoCriado1);
        mapProduto2.put("quantidade", 2);
        mapProduto2.put("produto", produtoCriado2);

        Cliente cliente = new Cliente(1L,
                "Jorge",
                "123.456.789-10",
                "07/12/2015",
                "",
                "(99) 99999-9999",
                "email@email.com",
                ""
        );

        Pedido pedido1 = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        Pedido pedido2 = new Pedido(2L, cliente, 2L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        when(clienteGateway.buscarPorId(any(Long.class))).thenReturn(cliente);
        when(pedidoGateway.buscarPorIdCliente(1L)).thenReturn(Arrays.asList(pedido1, pedido2));

        List<Pedido> resultado = buscarPedidosPorIdCliente.run(1L);

        assertFalse(resultado.isEmpty());
        assertEquals(1L, resultado.get(0).getCliente().getId());
        assertEquals(StatusPedido.ABERTO, resultado.get(0).getStatus());
        assertEquals(1L, resultado.get(1).getCliente().getId());
        assertEquals(StatusPedido.ABERTO, resultado.get(1).getStatus());
    }

    @Test
    public void deveLancarExcecaoQuandoClienteNaoExiste(){
        when(clienteGateway.buscarPorId(any(Long.class))).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> buscarPedidosPorIdCliente.run(1L));
    }
}
