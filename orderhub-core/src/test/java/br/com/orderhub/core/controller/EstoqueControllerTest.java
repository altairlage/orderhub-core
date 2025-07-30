package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoque;
import br.com.orderhub.core.domain.usecases.estoques.ConsultarEstoquePorSku;
import br.com.orderhub.core.domain.usecases.estoques.ReporEstoque;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueControllerTest {

    private BaixarEstoque baixarEstoque;
    private ReporEstoque reporEstoque;
    private ConsultarEstoquePorSku consultarEstoquePorSku;
    private EstoqueController controller;

    @BeforeEach
    void setUp() {
        baixarEstoque = mock(BaixarEstoque.class);
        reporEstoque = mock(ReporEstoque.class);
        consultarEstoquePorSku = mock(ConsultarEstoquePorSku.class);
        controller = new EstoqueController(baixarEstoque, reporEstoque, consultarEstoquePorSku);
    }

    @Test
    void deveReporEstoqueComSucesso() {
        Estoque estoque = new Estoque("1", 10, LocalDateTime.now(), LocalDateTime.now());
        when(consultarEstoquePorSku.run("1")).thenReturn(Optional.of(estoque));

        Estoque result = controller.repor("1", 5);

        assertEquals(estoque, result);
        verify(reporEstoque).executar("1", 5);
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        Estoque estoque = new Estoque("2", 8, LocalDateTime.now(), LocalDateTime.now());
        when(consultarEstoquePorSku.run("2")).thenReturn(Optional.of(estoque));

        Estoque result = controller.baixar("2", 3);

        assertEquals(estoque, result);
        verify(baixarEstoque).executar("2", 3);
    }

    @Test
    void deveLancarExcecaoAoConsultarEstoqueInexistente() {
        when(consultarEstoquePorSku.run("999")).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () -> controller.consultarPorSku("999"));
    }

    @Test
    void deveBaixarEstoquePorPedido() {
        ProdutoDTO produtoDTO1 = new ProdutoDTO(10L, "Produto A", "Descrição", 100.0);
        ProdutoDTO produtoDTO2 = new ProdutoDTO(20L, "Produto B", "Descrição", 200.0);

        Map<Integer, ProdutoDTO> item1 = Map.of(2, produtoDTO1);
        Map<Integer, ProdutoDTO> item2 = Map.of(1, produtoDTO2);
        List<Map<Integer, ProdutoDTO>> lista = List.of(item1, item2);

        PedidoDTO pedidoDTO = new PedidoDTO(1L, null, null, lista, null);

        Estoque estoque1 = new Estoque("10", 100, LocalDateTime.now(), LocalDateTime.now());
        Estoque estoque2 = new Estoque("20", 50, LocalDateTime.now(), LocalDateTime.now());

        when(consultarEstoquePorSku.run("10")).thenReturn(Optional.of(estoque1));
        when(consultarEstoquePorSku.run("20")).thenReturn(Optional.of(estoque2));

        controller.baixarEstoquePorPedido(pedidoDTO);

        verify(baixarEstoque).executar("10", 2);
        verify(baixarEstoque).executar("20", 1);
    }

}
