package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.utils.ClientesUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AtualizarClienteTest {
    private IClienteGateway gateway;
    private AtualizarCliente atualizarCliente;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        atualizarCliente = new AtualizarCliente(gateway);
    }

    @Test
    public void deveAtualizarCliente() {
        ClienteDTO clienteDTO = ClientesUtilsTest.criaClienteDto();
        Cliente clienteExistente = ClientesUtilsTest.criaCliente();
        Cliente clienteAtualizado = new Cliente(
                1L,
                "Jorge Silva Sauro",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito");

        when(gateway.buscarPorCpf(clienteExistente.getCpf())).thenReturn(clienteExistente);
        when(gateway.atualizar(any(Cliente.class))).thenReturn(clienteAtualizado);

        ClienteDTO resultado = atualizarCliente.run(clienteDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Jorge Silva Sauro", resultado.nome());
        assertEquals("123.456.789-09", resultado.cpf());
        assertEquals("07/12/2015", resultado.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.endereco());
        assertEquals("(99) 99999-9999", resultado.numeroContato());
        assertEquals("email@email.com", resultado.email());
        assertEquals("Cartao de Credito", resultado.infoPagamento());

        verify(gateway).atualizar(any(Cliente.class));
    }

    @Test
    public void deveLancarExceptionQuandoClienteNaoExistir() {
        ClienteDTO clienteDTO = ClientesUtilsTest.criaClienteDto();

        when(gateway.buscarPorCpf("321.456.789-09")).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> atualizarCliente.run(clienteDTO));

        verify(gateway, never()).atualizar(any());
    }
}