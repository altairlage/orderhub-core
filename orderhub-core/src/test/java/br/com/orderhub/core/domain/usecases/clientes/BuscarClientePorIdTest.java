package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.utils.ClientesUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuscarClientePorIdTest {
    private IClienteGateway gateway;
    private BuscarClientePorId buscarClientePorId;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        buscarClientePorId = new BuscarClientePorId(gateway);
    }

    @Test
    public void deveRetornarClientePorId() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorId(cliente.getId())).thenReturn(cliente);

        ClienteDTO resultado = buscarClientePorId.run(cliente.getId());

        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.id());
    }

    @Test
    public void deveLancarExceptionCasoClienteNaoExistir() {
        when(gateway.buscarPorId(999L)).thenReturn(null);
        assertThrows(ClienteNaoEncontradoException.class, () -> buscarClientePorId.run(999L));
    }
}