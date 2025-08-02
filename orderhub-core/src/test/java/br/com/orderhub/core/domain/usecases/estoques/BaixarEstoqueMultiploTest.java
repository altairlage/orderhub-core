package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaixarEstoqueMultiploTest {

    @Mock
    private IEstoqueGateway estoqueGateway;

    @InjectMocks
    private BaixarEstoqueMultiplo baixarEstoqueMultiplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveBaixarEstoqueDeMultiplosItensComSucesso() {
        // Arrange
        ProdutoDTO produto1 = new ProdutoDTO(1L, "Produto A", "Desc A", 10.0);
        ProdutoDTO produto2 = new ProdutoDTO(2L, "Produto B", "Desc B", 20.0);
        List<ItemEstoqueDTO> itensParaBaixa = List.of(
            new ItemEstoqueDTO(produto1, 5),
            new ItemEstoqueDTO(produto2, 10)
        );

        Estoque estoque1 = new Estoque(1L, 10, LocalDateTime.now(), null);
        Estoque estoque2 = new Estoque(2L, 20, LocalDateTime.now(), null);

        when(estoqueGateway.buscarPorIds(List.of(1L, 2L))).thenReturn(List.of(estoque1, estoque2));

        // Act
        baixarEstoqueMultiplo.executar(itensParaBaixa);

        // Assert
        verify(estoqueGateway, times(1)).salvar(estoque1);
        verify(estoqueGateway, times(1)).salvar(estoque2);
        assertEquals(5, estoque1.getQuantidadeDisponivel());
        assertEquals(10, estoque2.getQuantidadeDisponivel());
    }

    @Test
    void deveLancarExcecaoSeUmProdutoNaoForEncontrado() {
        // Arrange
        ProdutoDTO produto1 = new ProdutoDTO(1L, "Produto A", "Desc A", 10.0);
        ProdutoDTO produtoInexistente = new ProdutoDTO(99L, "Produto Inexistente", "Desc", 99.0);
        List<ItemEstoqueDTO> itensParaBaixa = List.of(
            new ItemEstoqueDTO(produto1, 5),
            new ItemEstoqueDTO(produtoInexistente, 1)
        );

        Estoque estoque1 = new Estoque(1L, 10, LocalDateTime.now(), null);

        // Apenas o estoque do produto 1 é encontrado
        when(estoqueGateway.buscarPorIds(List.of(1L, 99L))).thenReturn(List.of(estoque1));

        // Act & Assert
        EstoqueNaoEncontradoException exception = assertThrows(
            EstoqueNaoEncontradoException.class,
            () -> baixarEstoqueMultiplo.executar(itensParaBaixa)
        );

        assertTrue(exception.getMessage().contains("Estoque não encontrado para o produto: Produto Inexistente"));
        verify(estoqueGateway, never()).salvar(any()); // Nenhum estoque deve ser salvo
    }

    @Test
    void deveLancarExcecaoSeEstoqueForInsuficiente() {
        // Arrange
        ProdutoDTO produto1 = new ProdutoDTO(1L, "Produto A", "Desc A", 10.0);
        List<ItemEstoqueDTO> itensParaBaixa = List.of(
            new ItemEstoqueDTO(produto1, 15) // Tenta baixar 15, mas só tem 10
        );

        Estoque estoque1 = new Estoque(1L, 10, LocalDateTime.now(), null);

        when(estoqueGateway.buscarPorIds(List.of(1L))).thenReturn(List.of(estoque1));

        // Act & Assert
        EstoqueInsuficienteException exception = assertThrows(
            EstoqueInsuficienteException.class,
            () -> baixarEstoqueMultiplo.executar(itensParaBaixa)
        );

        assertTrue(exception.getMessage().contains("Estoque insuficiente para o produto Produto A"));
        verify(estoqueGateway, never()).salvar(any()); // Nenhum estoque deve ser salvo
    }
}