package br.com.orderhub.core.domain.usecases;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.usecases.produtos.CriarProduto;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;
import br.com.orderhub.core.exceptions.ProdutoJaExisteException;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CriarProdutoTest {

    private IProdutoGateway gateway;
    private CriarProduto criarProduto;

    @BeforeEach
    public void setUp() {
        gateway = mock(IProdutoGateway.class);
        criarProduto = new CriarProduto(gateway);
    }

    @Test
    public void deveCriarProdutoQuandoNaoExistirProdutoComMesmoNome() {
        CriarProdutoDTO dto = new CriarProdutoDTO("Café", "Café em pó", 10.0);
        when(gateway.buscarPorNome("Café")).thenReturn(null);

        Produto produtoCriado = new Produto("Café", "Café em pó", 10.0);
        when(gateway.criar(any(Produto.class))).thenReturn(produtoCriado);

        Produto resultado = criarProduto.run(dto);

        assertNotNull(resultado);
        assertEquals("Café", resultado.getNome());
        verify(gateway).criar(any(Produto.class));
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoJaExistir() {
        CriarProdutoDTO dto = new CriarProdutoDTO("Café", "Café em pó", 10.0);
        when(gateway.buscarPorNome("Café")).thenReturn(new Produto("Café", "Desc", 5.0));

        assertThrows(ProdutoJaExisteException.class, () -> criarProduto.run(dto));
        verify(gateway, never()).criar(any());
    }
}

