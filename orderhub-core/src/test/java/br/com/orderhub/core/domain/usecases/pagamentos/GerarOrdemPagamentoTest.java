package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.dto.pagamentos.CriarPagamentoDTO;
import br.com.orderhub.core.interfaces.IPagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GerarOrdemPagamentoTest {
    private IPagamentoGateway gateway;
    private GerarOrdemPagamento gerarOrdemPagamento;

    @BeforeEach
    public void setup() {
        gateway = mock(IPagamentoGateway.class);
        gerarOrdemPagamento = new GerarOrdemPagamento(gateway);
    }

    @Test
    public void deveGerarOrdemPagamentoComSucesso() throws Exception {
        Pagamento fakeRequest = new Pagamento("Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);

        CriarPagamentoDTO criarPagamentoDTO = new CriarPagamentoDTO(
                "Adamastor",
                "email@email.com",
                150.0,
                StatusPagamento.EM_ABERTO
        );
        Pagamento pagamentoMock = new Pagamento(1L, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);

        when(gateway.gerarOrdemPagamento(fakeRequest)).thenReturn(pagamentoMock);

        Pagamento resultado = gerarOrdemPagamento.run(criarPagamentoDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(gateway).gerarOrdemPagamento(fakeRequest);
    }

    @Test
    public void deveLancarRuntimeExceptionAoFalharGeracao() throws Exception {
        Pagamento fakeRequest = new Pagamento("Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);

        CriarPagamentoDTO criarPagamentoDTO = new CriarPagamentoDTO(
                "Adamastor",
                "email@email.com",
                150.0,
                StatusPagamento.EM_ABERTO
        );

        when(gateway.gerarOrdemPagamento(fakeRequest)).thenThrow(new RuntimeException("Falha interna"));

        Exception ex = assertThrows(RuntimeException.class, () -> gerarOrdemPagamento.run(criarPagamentoDTO));

        assertTrue(ex.getMessage().contains("Erro ao gerar ordem de pagamento"));
        assertNotNull(ex.getCause());
        assertEquals("Falha interna", ex.getCause().getMessage());
        verify(gateway).gerarOrdemPagamento(fakeRequest);
    }
}
