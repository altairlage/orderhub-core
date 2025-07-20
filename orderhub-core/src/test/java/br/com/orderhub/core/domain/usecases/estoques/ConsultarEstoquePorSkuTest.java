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

class ConsultarEstoquePorSkuTest {

    private IEstoqueGateway estoqueGateway;
    private ConsultarEstoquePorSku consultarEstoquePorSku;

    @BeforeEach
    void setUp() {
        estoqueGateway = mock(IEstoqueGateway.class);
        consultarEstoquePorSku = new ConsultarEstoquePorSku(estoqueGateway);
    }

    @Test
    void deveRetornarEstoqueQuandoSkuExistir() {
        String sku = "SKU123";
        Estoque estoque = new Estoque(sku, 10, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoque));

        Optional<Estoque> resultado = consultarEstoquePorSku.run(sku);

        assertTrue(resultado.isPresent());
        assertEquals(estoque, resultado.get());
    }

    @Test
    void deveRetornarOptionalVazioQuandoSkuNaoExistir() {
        String sku = "SKU_INEXISTENTE";

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.empty());

        Optional<Estoque> resultado = consultarEstoquePorSku.run(sku);

        assertFalse(resultado.isPresent());
    }
}
