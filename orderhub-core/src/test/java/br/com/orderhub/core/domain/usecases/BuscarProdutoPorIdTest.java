package br.com.orderhub.core.domain.usecases;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.usecases.produtos.BuscarProdutoPorId;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarProdutoPorIdTest {

    private IProdutoGateway gateway;
    private BuscarProdutoPorId buscarProdutoPorId;

    @BeforeEach
    public void setUp() {
        gateway = mock(IProdutoGateway.class);
        buscarProdutoPorId = new BuscarProdutoPorId(gateway);
    }

    @Test
    public void deveRetornarProdutoQuandoIdExiste() {
        Produto produto = new Produto("Arroz", "Arroz integral", 15.0);
        when(gateway.buscarPorId(1L)).thenReturn(produto);

        Produto resultado = buscarProdutoPorId.run(1L);

        assertNotNull(resultado);
        assertEquals("Arroz", resultado.getNome());
        verify(gateway).buscarPorId(1L);
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoExiste() {
        when(gateway.buscarPorId(2L)).thenReturn(null);

        assertThrows(ProdutoNaoEncontradoException.class, () -> buscarProdutoPorId.run(2L));
        verify(gateway).buscarPorId(2L);
    }
}
