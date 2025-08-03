package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.exceptions.OrdemPagamentoNaoEncontradaException;
import br.com.orderhub.core.interfaces.IPagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FecharOrdemPagamentoTest {
        private IPagamentoGateway gateway;
        private FecharOrdemPagamento fecharOrdemPagamento;

        @BeforeEach
        public void setup() {
            gateway = mock(IPagamentoGateway.class);
            fecharOrdemPagamento = new FecharOrdemPagamento(gateway);
        }

        @Test
        public void deveFecharOrdemPagamentoComStatusValido() {
            Long id = 1L;
            StatusPagamento status = StatusPagamento.FECHADO_COM_SUCESSO;
            Pagamento pagamentoMock = new Pagamento(id, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);

            when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(pagamentoMock);
            when(gateway.fecharOrdemPagamento(id, status)).thenReturn(new Pagamento(id, "Adamastor", "email@email.com", 150.0, status));

            Pagamento resultado = fecharOrdemPagamento.run(id, status);

            assertNotNull(resultado);
            assertEquals(status, resultado.getStatus());
            verify(gateway).buscarOrderPagamentoPorId(id);
            verify(gateway).fecharOrdemPagamento(id, status);
        }

        @Test
        public void deveLancarExcecaoSeStatusForNulo() {
            Long id = 1L;

            Exception ex = assertThrows(IllegalArgumentException.class, () -> fecharOrdemPagamento.run(id, null));

            assertEquals("Status nulo ou invalido", ex.getMessage());
        }

        @Test
        public void deveLancarExcecaoSeStatusNaoContemFechado() {
            Long id = 1L;

            Exception ex = assertThrows(IllegalArgumentException.class, () -> fecharOrdemPagamento.run(id, StatusPagamento.EM_ABERTO));

            assertEquals("Status nulo ou invalido", ex.getMessage());
        }

        @Test
        public void deveLancarExcecaoSePagamentoNaoForEncontrado() {
            Long id = 2L;
            StatusPagamento status = StatusPagamento.FECHADO_COM_SUCESSO;

            when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(null);

            Exception ex = assertThrows(OrdemPagamentoNaoEncontradaException.class, () -> fecharOrdemPagamento.run(id, status));

            assertEquals("Ordem de pagamento com id 2 não encontrada", ex.getMessage());
            verify(gateway).buscarOrderPagamentoPorId(id);
        }
}
