package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.exceptions.OrdemPagamentoNaoEncontradaException;
import br.com.orderhub.core.interfaces.IPagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarPagamentoPorIdTest {
    private IPagamentoGateway gateway;
    private BuscarPagamentoPorId buscarPagamentoPorId;

    @BeforeEach
    public void setup() {
        gateway = mock(IPagamentoGateway.class);
        buscarPagamentoPorId = new BuscarPagamentoPorId(gateway);
    }

    @Test
    public void deveRetornarPagamentoQuandoEncontrado() {
        Long id = 1L;
        Pagamento pagamentoMock = new Pagamento(id, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);
        when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(pagamentoMock);

        Pagamento resultado = buscarPagamentoPorId.run(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(gateway).buscarOrderPagamentoPorId(id);
    }

    @Test
    public void deveLancarExcecaoQuandoPagamentoNaoForEncontrado() {
        Long id = 2L;
        when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(null);

        Exception ex = assertThrows(OrdemPagamentoNaoEncontradaException.class, () -> buscarPagamentoPorId.run(id));

        assertEquals("Ordem de pagamento com idProduto 2 não encontrada", ex.getMessage());
        verify(gateway).buscarOrderPagamentoPorId(id);
    }
}
