package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.dto.produtos.CriarProdutoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoControllerTest {

    private IProdutoGateway gateway;
    private ProdutoController controller;

    @BeforeEach
    public void setUp() {
        gateway = mock(IProdutoGateway.class);
        controller = new ProdutoController(gateway);
    }

    @Test
    public void deveCriarProdutoComSucesso() {
        CriarProdutoDTO dto = new CriarProdutoDTO("Notebook", "Ultrabook", 4000.0);
        Produto criado = new Produto("Notebook", "Ultrabook", 4000.0);

        when(gateway.buscarPorNome("Notebook")).thenReturn(null);
        when(gateway.criar(any(Produto.class))).thenReturn(criado);

        ProdutoDTO resultado = controller.criarProduto(dto);

        assertNotNull(resultado);
        assertEquals("Notebook", resultado.nome());
        verify(gateway).criar(any(Produto.class));
    }

    @Test
    public void deveBuscarProdutoPorIdComSucesso() {
        Produto produto = new Produto("Mouse", "Sem fio", 150.0);
        when(gateway.buscarPorId(1L)).thenReturn(produto);

        ProdutoDTO resultado = controller.buscarProdutoPorId(1L);

        assertEquals("Mouse", resultado.nome());
        verify(gateway).buscarPorId(1L);
    }

    @Test
    public void deveBuscarProdutoPorNomeComSucesso() {
        Produto produto = new Produto("Teclado", "Mecânico", 300.0);
        when(gateway.buscarPorNome("Teclado")).thenReturn(produto);

        ProdutoDTO resultado = controller.buscarProdutoPorNome("Teclado");

        assertEquals("Teclado", resultado.nome());
        verify(gateway).buscarPorNome("Teclado");
    }

    @Test
    public void deveEditarProdutoComSucesso() {
        ProdutoDTO dto = new ProdutoDTO(2L, "Monitor", "4K", 1200.0);
        Produto existente = new Produto(2L, "Monitor", "FullHD", 900.0);
        Produto atualizado = new Produto(2L, "Monitor", "4K", 1200.0);

        when(gateway.buscarPorId(2L)).thenReturn(existente);
        when(gateway.atualizar(any(Produto.class))).thenReturn(atualizado);

        ProdutoDTO resultado = controller.editarProduto(dto);

        assertEquals("4K", resultado.descricao());
        assertEquals(1200.0, resultado.preco());
        verify(gateway).atualizar(any(Produto.class));
    }

    @Test
    public void deveDeletarProdutoComSucesso() {
        Produto produto = new Produto(3L, "Webcam", "HD", 250.0);
        when(gateway.buscarPorId(3L)).thenReturn(produto);

        doNothing().when(gateway).deletar(3L);

        controller.deletarProduto(3L);

        verify(gateway).deletar(3L);
    }
}
