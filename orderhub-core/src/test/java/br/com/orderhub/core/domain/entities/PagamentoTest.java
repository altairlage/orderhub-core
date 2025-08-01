package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.domain.enums.StatusPagamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PagamentoTest {
        @Test
        public void deveCriarPagamentoComIdEStatus() {
            Pagamento pagamento = new Pagamento(1L, StatusPagamento.EM_ABERTO);

            assertEquals(1L, pagamento.getId());
            assertEquals(StatusPagamento.EM_ABERTO, pagamento.getStatus());
        }

        @Test
        public void deveCriarPagamentoSomenteComStatus() {
            Pagamento pagamento = new Pagamento(StatusPagamento.EM_ABERTO);

            assertNull(pagamento.getId());
            assertEquals(StatusPagamento.EM_ABERTO, pagamento.getStatus());
        }

        @Test
        public void devePermitirAlterarStatusDeAbertoParaFechado() {
            Pagamento pagamento = new Pagamento(1L, StatusPagamento.EM_ABERTO);

            pagamento.setStatus(StatusPagamento.FECHADO_COM_SUCESSO);

            assertEquals(StatusPagamento.FECHADO_COM_SUCESSO, pagamento.getStatus());
        }

        @Test
        public void deveLancarExcecaoAoAlterarStatusDeAbertoParaNaoFechado() {
            Pagamento pagamento = new Pagamento(1L, StatusPagamento.EM_ABERTO);

            Exception ex = assertThrows(IllegalArgumentException.class, () -> {
                pagamento.setStatus(StatusPagamento.EM_ABERTO);
            });

            assertEquals("Ordem de Pagamento com status 'ABERTO' só pode ser fechada", ex.getMessage());
        }

        @Test
        public void devePermitirAlterarStatusSeNaoEstiverAberto() {
            Pagamento pagamento = new Pagamento(1L, StatusPagamento.FECHADO_FALHA_PAGAMENTO);

            pagamento.setStatus(StatusPagamento.FECHADO_COM_SUCESSO);

            assertEquals(StatusPagamento.FECHADO_COM_SUCESSO, pagamento.getStatus());
        }
}
