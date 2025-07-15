package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditarProdutoTest {

    private IProdutoGateway gateway;
    private EditarProduto editarProduto;

    @BeforeEach
    public void setUp() {
        gateway = mock(IProdutoGateway.class);
        editarProduto = new EditarProduto(gateway);
    }

    @Test
    public void deveEditarProdutoQuandoExistir() {
        ProdutoDTO dto = new ProdutoDTO(1L, "Café", "Café especial", 20.0);
        br.com.orderhub.core.domain.entities.Produto existente = new br.com.orderhub.core.domain.entities.Produto(1L, "Café", "Desc", 15.0);
        br.com.orderhub.core.domain.entities.Produto atualizado = new br.com.orderhub.core.domain.entities.Produto(1L, "Café", "Café especial", 20.0);

        when(gateway.buscarPorId(1L)).thenReturn(existente);
        when(gateway.atualizar(any(br.com.orderhub.core.domain.entities.Produto.class))).thenReturn(atualizado);

        br.com.orderhub.core.domain.entities.Produto resultado = editarProduto.run(dto);

        assertNotNull(resultado);
        assertEquals("Café especial", resultado.getDescricao());
        assertEquals(20.0, resultado.getPreco());
        verify(gateway).atualizar(any(br.com.orderhub.core.domain.entities.Produto.class));
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoExistir() {
        ProdutoDTO dto = new ProdutoDTO(999L, "X", "Y", 0.0);
        when(gateway.buscarPorId(999L)).thenReturn(null);

        assertThrows(ProdutoNaoEncontradoException.class, () -> editarProduto.run(dto));
        verify(gateway, never()).atualizar(any());
    }
}
