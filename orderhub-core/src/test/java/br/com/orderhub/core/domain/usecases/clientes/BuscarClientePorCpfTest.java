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

class BuscarClientePorCpfTest {
    private IClienteGateway gateway;
    private BuscarClientePorCpf buscarClientePorCpf;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        buscarClientePorCpf = new BuscarClientePorCpf(gateway);
    }

    @Test
    public void deveRetornarClientePorCpf() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorCpf(cliente.getCpf())).thenReturn(cliente);

        ClienteDTO resultado = buscarClientePorCpf.run(cliente.getCpf());

        assertNotNull(resultado);
        assertEquals(cliente.getCpf(), resultado.cpf());
    }

    @Test
    public void deveLancarExceptionCasoClienteNaoExistir() {
        when(gateway.buscarPorCpf("231.577.970-76")).thenReturn(null);
        assertThrows(ClienteNaoEncontradoException.class, () -> buscarClientePorCpf.run("231.577.970-76"));
    }
}