package br.com.orderhub.core.domain.usecases.estoques;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

class ReporEstoqueTest {

    private IEstoqueGateway estoqueGateway;
    private ReporEstoque reporEstoque;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        reporEstoque = new ReporEstoque(estoqueGateway);
    }

    @Test
    void deveReporEstoqueComSucesso() {
        Long id = 1L;
        int quantidadeReposta = 5;
        Estoque estoque = new Estoque(id, 10, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.of(estoque));

        reporEstoque.executar(id, quantidadeReposta);

        assertEquals(15, estoque.getQuantidadeDisponivel());
        verify(estoqueGateway).salvar(estoque);
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNaoEncontrado() {
        Long id = 999L;

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.empty());

        EstoqueNaoEncontradoException exception = assertThrows(
                EstoqueNaoEncontradoException.class,
                () -> reporEstoque.executar(id, 3)
        );

        assertTrue(exception.getMessage().contains("Estoque não encontrado"));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeMenorOuIgualAZero() {
        Long id = 1L;
        Estoque estoque = new Estoque(id, 10, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.of(estoque));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reporEstoque.executar(id, 0)
        );

        assertEquals("Quantidade para reposição deve ser maior que zero.", exception.getMessage());
    }
}