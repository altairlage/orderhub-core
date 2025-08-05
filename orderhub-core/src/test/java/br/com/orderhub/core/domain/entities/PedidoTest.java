package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.domain.enums.StatusPedido;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    public void deveCriarPedidoComSucesso(){
        Pagamento pagamentoCriado = new Pagamento(1L, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);
        Produto produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        Produto produtoCriado2 = new Produto("Feijão", "Preto", 20.0);
        Map<String, Object>  mapProduto1 = new HashMap<>();
        Map<String, Object> mapProduto2 = new HashMap<>();
        mapProduto1.put("quantidade", 3);
        mapProduto1.put("produto", produtoCriado1);
        mapProduto2.put("quantidade", 2);
        mapProduto2.put("produto", produtoCriado2);

        Long idCliente = 1L;
        Long idPagamento1 = 1L;

        Pedido resultado = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        assertEquals(1L, resultado.getIdPedido());
        assertEquals(StatusPedido.ABERTO, resultado.getStatus());
        assertEquals(idCliente, resultado.getIdCliente());
        assertEquals(idPagamento1, resultado.getIdPagamento());
        assertFalse(resultado.getListaQtdProdutos().isEmpty());

    }

    @Test
    public void deveCompararPorEqualsComSucesso(){
        Pagamento pagamentoCriado = new Pagamento(1L, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);
        Produto produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        Produto produtoCriado2 = new Produto("Feijão", "Preto", 20.0);
        Map<String, Object>  mapProduto1 = new HashMap<>();
        Map<String, Object> mapProduto2 = new HashMap<>();
        mapProduto1.put("quantidade", 1);
        mapProduto1.put("produto", produtoCriado1);
        mapProduto2.put("quantidade", 2);
        mapProduto2.put("produto", produtoCriado2);

        Long idCliente = 1L;
        Long idPagamento1 = 1L;

        Pedido pedido1 = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        Pedido pedido2 = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        assertEquals(pedido1, pedido2);
    }

    @Test
    public void deveGerarHashCodeConsistente(){
        Pagamento pagamentoCriado = new Pagamento(1L, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);
        Produto produtoCriado1 = new Produto("Arroz", "Branco", 20.0);
        Produto produtoCriado2 = new Produto("Feijão", "Preto", 20.0);
        Map<String, Object>  mapProduto1 = new HashMap<>();
        Map<String, Object> mapProduto2 = new HashMap<>();
        mapProduto1.put("quantidade", 1);
        mapProduto1.put("produto", produtoCriado1);
        mapProduto2.put("quantidade", 2);
        mapProduto2.put("produto", produtoCriado2);

        Long idCliente = 1L;
        Long idPagamento1 = 1L;

        Pedido pedido1 = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
        Pedido pedido2 = new Pedido(1L, idCliente, idPagamento1, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);

        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }
}
