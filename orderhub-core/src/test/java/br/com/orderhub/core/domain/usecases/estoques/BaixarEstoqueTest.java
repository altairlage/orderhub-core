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
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

class BaixarEstoqueTest {

    private IEstoqueGateway estoqueGateway;
    private BaixarEstoque baixarEstoque;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        baixarEstoque = new BaixarEstoque(estoqueGateway);
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        String sku = "SKU123";
        int quantidadeSolicitada = 3;
        Estoque estoque = new Estoque(
                sku,
                10,
                LocalDateTime.now(),
                LocalDateTime.now());

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        baixarEstoque.executar(sku, quantidadeSolicitada);

        assertEquals(7, estoque.getQuantidadeDisponivel());
        verify(estoqueGateway).salvar(estoque);
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNaoEncontrado() {
        String sku = "INEXISTENTE";

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        EstoqueNaoEncontradoException exception = assertThrows(
                EstoqueNaoEncontradoException.class,
                () -> baixarEstoque.executar(sku, 5));

        assertTrue(exception.getMessage().contains("Estoque não encontrado"));
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        String sku = "SKU123";
        Estoque estoque = new Estoque(
                sku,
                2,
                LocalDateTime.now(),
                LocalDateTime.now());

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        EstoqueInsuficienteException exception = assertThrows(
                EstoqueInsuficienteException.class,
                () -> baixarEstoque.executar(sku, 5));

        assertTrue(exception.getMessage().contains("Estoque insuficiente"));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeMenorOuIgualAZero() {
        String sku = "SKU123";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> baixarEstoque.executar(sku, 0));

        assertEquals("A quantidade solicitada deve ser maior que zero.", exception.getMessage());
    }

}
