package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarProdutoDTOPorNomeTest {

    private IProdutoGateway gateway;
    private BuscarProdutoPorNome buscarProdutoPorNome;

    @BeforeEach
    public void setUp() {
        gateway = mock(IProdutoGateway.class);
        buscarProdutoPorNome = new BuscarProdutoPorNome(gateway);
    }

    @Test
    public void deveRetornarProdutoQuandoNomeExistir() {
        Produto produto = new Produto("Açúcar", "Açúcar cristal", 7.5);
        when(gateway.buscarPorNome("Açúcar")).thenReturn(produto);

        Produto resultado = buscarProdutoPorNome.run("Açúcar");

        assertNotNull(resultado);
        assertEquals("Açúcar", resultado.getNome());
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoExistir() {
        when(gateway.buscarPorNome("Inexistente")).thenReturn(null);

        assertThrows(ProdutoNaoEncontradoException.class, () -> buscarProdutoPorNome.run("Inexistente"));
    }
}
