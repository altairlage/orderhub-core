package br.com.orderhub.core.domain.usecases.pagamentos;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
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
        ClienteDTO clienteDTO = new ClienteDTO(
                1L,
                "Adamastor",
                "123.456.789-09",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );
        Pagamento pagamentoMock = new Pagamento(1L, StatusPagamento.EM_ABERTO);

        when(gateway.gerarOrdemPagamento(clienteDTO)).thenReturn(pagamentoMock);

        Pagamento resultado = gerarOrdemPagamento.run(clienteDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(gateway).gerarOrdemPagamento(clienteDTO);
    }

    @Test
    public void deveLancarRuntimeExceptionAoFalharGeracao() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO(
                1L,
                "Adamastor",
                "123.456.789-09",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        when(gateway.gerarOrdemPagamento(clienteDTO)).thenThrow(new RuntimeException("Falha interna"));

        Exception ex = assertThrows(RuntimeException.class, () -> {
            gerarOrdemPagamento.run(clienteDTO);
        });

        assertTrue(ex.getMessage().contains("Erro ao gerar ordem de pagamento"));
        assertNotNull(ex.getCause());
        assertEquals("Falha interna", ex.getCause().getMessage());
        verify(gateway).gerarOrdemPagamento(clienteDTO);
    }
}
