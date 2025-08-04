package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.ProdutoJaCadastradoNoEstoqueException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AdicionarProdutoNoEstoqueTest {
    private IEstoqueGateway estoqueGateway;
    private AdicionarProdutoNoEstoque adicionarProdutoNoEstoque;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        adicionarProdutoNoEstoque = new AdicionarProdutoNoEstoque(estoqueGateway);
    }

    @Test
    void deveAdicionarProdutoComSucesso() {
        Estoque novoProduto = new Estoque(1L, 10);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(null);
        when(estoqueGateway.adicionarProdutoNoEstoque(novoProduto)).thenReturn(novoProduto);

        Estoque resultado = adicionarProdutoNoEstoque.run(novoProduto);

        assertEquals(novoProduto, resultado);
        verify(estoqueGateway).adicionarProdutoNoEstoque(novoProduto);
    }

    @Test
    void deveLancarExcecaoParaQuantidadeNegativa() {
        Estoque estoqueInvalido = new Estoque(1L, -5);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> adicionarProdutoNoEstoque.run(estoqueInvalido));
        assertEquals("Quantidade inicial de produto no estoque nula ou inválida", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoParaProdutoJaExistente() {
        Estoque novoProduto = new Estoque(1L, 10);
        Estoque existente = new Estoque(1L, 5);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(existente);

        Exception ex = assertThrows(ProdutoJaCadastradoNoEstoqueException.class, () -> adicionarProdutoNoEstoque.run(novoProduto));
        assertEquals("Produto de ID 1 já existe no estoque", ex.getMessage());
    }
}
