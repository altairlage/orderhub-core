package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.AtualizarEstoqueDTO;
import br.com.orderhub.core.dto.estoques.EstoqueDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueControllerTest {
    private IEstoqueGateway estoqueGateway;
    private EstoqueController estoqueController;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        estoqueController = new EstoqueController(estoqueGateway);
    }

    @Test
    void deveBaixarEstoqueComSucesso() throws EstoqueNaoEncontradoException {
        AtualizarEstoqueDTO dto = new AtualizarEstoqueDTO(1L, 5);
        Estoque estoque = new Estoque(1L, 10);
        Estoque estoqueAtualizado = new Estoque(1L, 5);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(estoque);
        when(estoqueGateway.baixarEstoque(any())).thenReturn(estoqueAtualizado);

        EstoqueDTO resultado = estoqueController.baixarEstoque(dto);

        assertEquals(1L, resultado.idProduto());
        assertEquals(5, resultado.quantidadeDisponivel());
    }

    @Test
    void deveReporEstoqueComSucesso() throws EstoqueNaoEncontradoException {
        AtualizarEstoqueDTO dto = new AtualizarEstoqueDTO(1L, 5);
        Estoque estoque = new Estoque(1L, 10);
        Estoque estoqueAtualizado = new Estoque(1L, 15);

        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(estoque);
        when(estoqueGateway.reporEstoque(any())).thenReturn(estoqueAtualizado);

        EstoqueDTO resultado = estoqueController.reporEstoque(dto);

        assertEquals(15, resultado.quantidadeDisponivel());
    }

    @Test
    void deveConsultarEstoquePorIdProduto() throws EstoqueNaoEncontradoException {
        Estoque estoque = new Estoque(1L, 20);
        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(estoque);

        EstoqueDTO resultado = estoqueController.consultarEstoquePorIdProduto(1L);

        assertEquals(20, resultado.quantidadeDisponivel());
    }

    @Test
    void deveAdicionarProdutoNoEstoque() throws EstoqueNaoEncontradoException {
        AtualizarEstoqueDTO dto = new AtualizarEstoqueDTO(2L, 10);
        Estoque novoEstoque = new Estoque(2L, 10);

        when(estoqueGateway.adicionarProdutoNoEstoque(any())).thenReturn(novoEstoque);

        EstoqueDTO resultado = estoqueController.adicionarProdutoNoEstoque(dto);

        assertEquals(2L, resultado.idProduto());
        assertEquals(10, resultado.quantidadeDisponivel());
    }

    @Test
    void deveRemoverProdutoDoEstoque() throws EstoqueNaoEncontradoException {
        Estoque estoque = new Estoque(1L, 20);
        when(estoqueGateway.consultarEstoquePorIdProduto(1L)).thenReturn(estoque);
        doNothing().when(estoqueGateway).removerProdutoNoEstoque(1L);

        assertDoesNotThrow(() -> estoqueController.removerProdutoNoEstoque(1L));
        verify(estoqueGateway, times(1)).removerProdutoNoEstoque(1L);
    }
}