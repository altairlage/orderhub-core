package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.dto.pagamentos.CriarPagamentoDTO;
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
        Pagamento pagamentoMock = new Pagamento(id, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);
        when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(pagamentoMock);

        PagamentoDTO dto = controller.buscarPagamentoPorId(id);

        assertNotNull(dto);
        verify(gateway).buscarOrderPagamentoPorId(id);
    }

    @Test
    public void deveFecharOrdemPagamento() {
        Long id = 2L;
        StatusPagamento status = StatusPagamento.FECHADO_COM_SUCESSO;
        Pagamento pagamentoMock = new Pagamento(id, "Maristela", "email@email.com", 250.0, status);
        when(gateway.buscarOrderPagamentoPorId(id)).thenReturn(pagamentoMock);
        when(gateway.fecharOrdemPagamento(id, status)).thenReturn(pagamentoMock);

        PagamentoDTO dto = controller.fecharOrdemPagamento(id, status);

        assertNotNull(dto);
        verify(gateway).fecharOrdemPagamento(id, status);
    }

    @Test
    public void deveGerarOrdemPagamento() throws Exception {
        Pagamento fakeRequest = new Pagamento("Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);

        CriarPagamentoDTO criarPagamentoDTO = new CriarPagamentoDTO(
                "Adamastor",
                "email@email.com",
                150.0,
                StatusPagamento.EM_ABERTO
        );

        Pagamento pagamentoMock = new Pagamento(1L, "Adamastor", "email@email.com", 150.0, StatusPagamento.EM_ABERTO);
        when(gateway.gerarOrdemPagamento(fakeRequest)).thenReturn(pagamentoMock);

        PagamentoDTO dto = controller.gerarOrdemPagamento(criarPagamentoDTO);

        assertNotNull(dto);
        verify(gateway).gerarOrdemPagamento(fakeRequest);
    }
}
