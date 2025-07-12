package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.exceptions.ProdutoNaoEncontradoException;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeletarProdutoDTOTest {

    private IProdutoGateway gateway;
    private DeletarProduto deletarProduto;

    @BeforeEach
    public void setUp() {
        gateway = mock(IProdutoGateway.class);
        deletarProduto = new DeletarProduto(gateway);
    }

    @Test
    public void deveDeletarProdutoQuandoExistir() {
        Produto produto = new Produto("Arroz", "Integral", 9.0);
        when(gateway.buscarPorId(1L)).thenReturn(produto);

        deletarProduto.run(1L);

        verify(gateway).deletar(1L);
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoExistir() {
        when(gateway.buscarPorId(999L)).thenReturn(null);

        assertThrows(ProdutoNaoEncontradoException.class, () -> deletarProduto.run(999L));
        verify(gateway, never()).deletar(any());
    }
}
