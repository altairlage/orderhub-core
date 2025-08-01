package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.pagamentos.PagamentoDTO;
import br.com.orderhub.core.interfaces.IPagamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PagamentoControllerTest {
    private IPagamentoGateway gateway;
    private PagamentoController controller;

    @BeforeEach
    public void setup() {
        gateway = mock(IPagamentoGateway.class);
        controller = new PagamentoController(gateway);
    }

    @Test
    public void deveBuscarPagamentoPorId() {
        Long id = 1L;
        Pagamento pagamentoMock = new Pagamento(id, StatusPagamento.EM_ABERTO);
        when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(pagamentoMock);

        PagamentoDTO dto = controller.buscarPagamentoPorId(id);

        assertNotNull(dto);
        verify(gateway).buscarOrderPagamentoPorId(id);
    }

    @Test
    public void deveFecharOrdemPagamento() {
        Long id = 2L;
        StatusPagamento status = StatusPagamento.FECHADO_COM_SUCESSO;
        Pagamento pagamentoMock = new Pagamento(id, status);
        when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(pagamentoMock);
        when(gateway.fecharOrdemPagamento(id, status)).thenReturn(pagamentoMock);

        PagamentoDTO dto = controller.fecharOrdemPagamento(id, status);

        assertNotNull(dto);
        verify(gateway).fecharOrdemPagamento(id, status);
    }

    @Test
    public void deveGerarOrdemPagamento() throws Exception {
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

        PagamentoDTO dto = controller.gerarOrdemPagamento(clienteDTO);

        assertNotNull(dto);
        verify(gateway).gerarOrdemPagamento(clienteDTO);
    }
}
