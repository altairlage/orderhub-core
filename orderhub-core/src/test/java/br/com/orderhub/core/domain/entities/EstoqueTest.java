package br.com.orderhub.core.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class EstoqueTest {
    @Test
    void deveCriarEstoqueComValoresValidos() {
        Estoque estoque = new Estoque(1L, 10);

        assertEquals(1L, estoque.getIdProduto());
        assertEquals(10, estoque.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoParaIdProdutoInvalido() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Estoque(0L, 10));
        assertEquals("ID de Produto nulo ou inválido", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoParaQuantidadeNula() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Estoque(1L, null));
        assertEquals("Quantidade disponível para produto nulo", ex.getMessage());
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        Estoque estoque = new Estoque(1L, 10);
        estoque.baixarEstoque(5);

        assertEquals(5, estoque.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoAoBaixarEstoqueComQuantidadeZero() {
        Estoque estoque = new Estoque(1L, 10);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> estoque.baixarEstoque(0));
        assertEquals("Quantidade para baixa deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoAoBaixarEstoqueComQuantidadeMaiorQueDisponivel() {
        Estoque estoque = new Estoque(1L, 5);

        Exception ex = assertThrows(IllegalStateException.class, () -> estoque.baixarEstoque(10));
        assertEquals("Estoque insuficiente para a operação.", ex.getMessage());
    }

    @Test
    void deveReporEstoqueComSucesso() {
        Estoque estoque = new Estoque(1L, 10);
        estoque.reporEstoque(5);

        assertEquals(15, estoque.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoAoReporEstoqueComQuantidadeZero() {
        Estoque estoque = new Estoque(1L, 10);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> estoque.reporEstoque(0));
        assertEquals("Quantidade para reposição deve ser maior que zero.", ex.getMessage());
    }
}