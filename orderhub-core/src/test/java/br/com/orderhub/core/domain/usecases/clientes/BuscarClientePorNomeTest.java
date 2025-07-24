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

class BuscarClientePorNomeTest {
    private IClienteGateway gateway;
    private BuscarClientePorNome buscarClientePorNome;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        buscarClientePorNome = new BuscarClientePorNome(gateway);
    }

    @Test
    public void deveRetornarClientePorNome() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorNome(cliente.getNome())).thenReturn(cliente);

        ClienteDTO resultado = buscarClientePorNome.run(cliente.getNome());

        assertNotNull(resultado);
        assertEquals(cliente.getNome(), resultado.nome());
    }

    @Test
    public void deveLancarExceptionCasoClienteNaoExistir() {
        when(gateway.buscarPorNome("Nenhum Cliente")).thenReturn(null);
        assertThrows(ClienteNaoEncontradoException.class, () -> buscarClientePorNome.run("Nenhum Cliente"));
    }
}