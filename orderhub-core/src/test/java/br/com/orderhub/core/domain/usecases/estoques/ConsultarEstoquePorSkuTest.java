package br.com.orderhub.core.domain.usecases.estoques;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

class ConsultarEstoquePorIdTest {

    private IEstoqueGateway estoqueGateway;
    private ConsultarEstoquePorId consultarEstoquePorId;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        consultarEstoquePorId = new ConsultarEstoquePorId(estoqueGateway);
    }

    @Test
    void deveRetornarEstoqueQuandoIdExistir() {
        Long id = 1L;
        Estoque estoque = new Estoque(id, 10, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.of(estoque));

        Optional<Estoque> resultado = consultarEstoquePorId.run(id);

        assertTrue(resultado.isPresent());
        assertEquals(estoque, resultado.get());
    }

    @Test
    void deveRetornarOptionalVazioQuandoIdNaoExistir() {
        Long id = 999L;

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.empty());

        Optional<Estoque> resultado = consultarEstoquePorId.run(id);

        assertFalse(resultado.isPresent());
    }
}