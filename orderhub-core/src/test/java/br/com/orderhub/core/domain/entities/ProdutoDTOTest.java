package br.com.orderhub.core.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoDTOTest {

    @Test
    public void deveCriarProdutoComDadosCorretos() {
        Produto produto = new Produto(1L, "Feijão", "Feijão carioca", 12.0);

        assertEquals(1L, produto.getId());
        assertEquals("Feijão", produto.getNome());
        assertEquals("Feijão carioca", produto.getDescricao());
        assertEquals(12.0, produto.getPreco());
    }

    @Test
    public void deveCompararProdutosPorEquals() {
        Produto p1 = new Produto(1L, "Arroz", "Branco", 10.0);
        Produto p2 = new Produto(1L, "Arroz", "Branco", 10.0);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void deveGerarHashCodeConsistente() {
        Produto p1 = new Produto(1L, "Arroz", "Branco", 10.0);
        Produto p2 = new Produto(1L, "Arroz", "Branco", 10.0);

        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
