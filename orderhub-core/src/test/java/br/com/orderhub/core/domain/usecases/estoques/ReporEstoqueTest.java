package br.com.orderhub.core.domain.usecases.estoques;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

class ReporEstoqueTest {
    private IEstoqueGateway estoqueGateway;
    private ReporEstoque reporEstoque;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        reporEstoque = new ReporEstoque(estoqueGateway);
    }

    @Test
    void deveReporEstoqueComSucesso() {
        Estoque pedido = new Estoque(1L, 5);
        Estoque atual = new Estoque(1L, 10);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(atual);
        when(estoqueGateway.reporEstoque(pedido)).thenReturn(new Estoque(1L, 15));

        Estoque resultado = reporEstoque.run(pedido);

        assertEquals(1L, resultado.getIdProduto());
        assertEquals(15, resultado.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoParaQuantidadeZeroOuNegativa() {
        Estoque pedido = new Estoque(1L, 0);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> reporEstoque.run(pedido));
        assertEquals("A quantidade solicitada para repor o estoque deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoEncontrado() {
        Estoque pedido = new Estoque(99L, 5);

        when(estoqueGateway.consultarEstoquePorIdProduto(99L)).thenReturn(null);

        Exception ex = assertThrows(EstoqueNaoEncontradoException.class, () -> reporEstoque.run(pedido));
        assertEquals("Produto de ID 99 não encontrado no estoque", ex.getMessage());
    }
}