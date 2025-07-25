package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    public void deveCriarPedidoComSucesso(){
        Produto produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        Produto produtoCriado2 = new Produto("Feijão", "Preto", 20.0);
        Map<String, Object>  mapProduto1 = new HashMap<>();
        Map<String, Object> mapProduto2 = new HashMap<>();
        mapProduto1.put("quantidade", 3);
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

        Pedido resultado = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        assertEquals(1L, resultado.getIdPedido());
        assertEquals(StatusPedido.ABERTO, resultado.getStatus());
        assertEquals(1L, resultado.getCliente().getId());
        assertEquals(1L, resultado.getIdPagamento());
        assertEquals(100.0, resultado.calcularValorTotal());
        assertFalse(resultado.getListaQtdProdutos().isEmpty());

    }

    @Test
    public void deveCompararPorEqualsComSucesso(){
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
                "");

        Pedido pedido1 = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        Pedido pedido2 = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        assertEquals(pedido1, pedido2);
    }

    @Test
    public void deveGerarHashCodeConsistente(){
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
                "");

        Pedido pedido1 = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        Pedido pedido2 = new Pedido(1L, cliente, 1L, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }
}
