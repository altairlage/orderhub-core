package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.utils.ClientesUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoverClienteTest {

    private IClienteGateway gateway;
    private RemoverCliente removerCliente;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        removerCliente = new RemoverCliente(gateway);
    }

    @Test
    public void deveDeletarClienteSeExistir() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorId(cliente.getId())).thenReturn(cliente);

        removerCliente.run(cliente.getId());

        verify(gateway).remover(cliente.getId());
    }

    @Test
    public void lancarExcecaoSeClienteNaoExistir() {
        when(gateway.buscarPorId(999L)).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> removerCliente.run(999L));

        verify(gateway, never()).remover(any());
    }
}