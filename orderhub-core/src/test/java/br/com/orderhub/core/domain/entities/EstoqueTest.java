package br.com.orderhub.core.domain.entities;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstoqueTest {

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        // CORREÇÃO: Usa um Long (1L) em vez de uma String para o ID
        estoque = new Estoque(1L, 10, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void deveBaixarEstoqueCorretamente() {
        estoque.baixarEstoque(4);
        assertEquals(6, estoque.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoAoBaixarComQuantidadeZeroOuNegativa() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> estoque.baixarEstoque(0)
        );
        assertEquals("Quantidade para baixa deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveReporEstoqueCorretamente() {
        estoque.reporEstoque(5);
        assertEquals(15, estoque.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoAoReporComQuantidadeZeroOuNegativa() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> estoque.reporEstoque(-3)
        );
        assertEquals("Quantidade para reposição deve ser maior que zero.", ex.getMessage());
    }
}