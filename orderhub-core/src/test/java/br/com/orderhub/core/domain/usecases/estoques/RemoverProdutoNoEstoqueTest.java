package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoverProdutoNoEstoqueTest {
    private IEstoqueGateway estoqueGateway;
    private RemoverProdutoNoEstoque removerProduto;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        removerProduto = new RemoverProdutoNoEstoque(estoqueGateway);
    }

    @Test
    void deveRemoverProdutoComSucesso() {
        Estoque produto = new Estoque(1L, 10);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(produto);
        doNothing().when(estoqueGateway).removerProdutoNoEstoque(1L);

        assertDoesNotThrow(() -> removerProduto.run(1L));
        verify(estoqueGateway).removerProdutoNoEstoque(1L);
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoEncontrado() {
        when(estoqueGateway.consultarEstoquePorIdProduto(99L)).thenReturn(null);

        Exception ex = assertThrows(EstoqueNaoEncontradoException.class, () -> removerProduto.run(99L));
        assertEquals("Produto de ID 99 não encontrado no estoque", ex.getMessage());
    }
}