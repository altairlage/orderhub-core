package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTodosClientesTest {
    private IClienteGateway gateway;
    private ListarTodosClientes listarTodosClientes;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        listarTodosClientes = new ListarTodosClientes(gateway);
    }

    @Test
    public void deveRetornarListaClientes() {
        Cliente cliente1 = new Cliente(1L, "Jorge Silva Sauro", "123.456.789-09", "07/12/2015", "Rua Teste, 123, Bairro XYZ", "(99) 99999-9999", "email@email.com", "Cartao de Credito");
        Cliente cliente2 = new Cliente(2L, "Jonas", "776.311.789-29", "01/02/2015", "Rua Teste, 123, Bairro XYZ", "(99) 99999-9999", "email@email.com", "Cartao de Credito");

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(cliente1);
        listaClientes.add(cliente2);

        when(gateway.listarTodos()).thenReturn(listaClientes);

        List<ClienteDTO> resultado = listarTodosClientes.run();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(cliente1.getId(), resultado.get(0).id());
        assertEquals(cliente2.getId(), resultado.get(1).id());

        verify(gateway, times(1)).listarTodos();
    }
}