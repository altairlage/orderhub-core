package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsultarEstoquePorIdProdutoTest {
    private IEstoqueGateway estoqueGateway;
    private ConsultarEstoquePorIdProduto consultarEstoque;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        consultarEstoque = new ConsultarEstoquePorIdProduto(estoqueGateway);
    }

    @Test
    void deveRetornarEstoqueQuandoProdutoExiste() {
        Estoque produto = new Estoque(1L, 20);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(produto);

        Estoque resultado = consultarEstoque.run(1L);

        assertEquals(1L, resultado.getIdProduto());
        assertEquals(20, resultado.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExiste() {
        when(estoqueGateway.consultarEstoquePorIdProduto(99L)).thenReturn(null);

        Exception ex = assertThrows(EstoqueNaoEncontradoException.class, () -> consultarEstoque.run(99L));
        assertEquals("Produto de ID 99 não encontrado no estoque", ex.getMessage());
    }
}
