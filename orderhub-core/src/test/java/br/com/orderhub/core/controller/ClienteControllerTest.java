package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.utils.ClientesUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {
    private IClienteGateway gateway;
    private ClienteController clienteController;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        clienteController = new ClienteController(gateway);
    }

    @Test
    public void deveCriarClienteCasoNaoExista() {
        CriarClienteDTO clienteDTO = ClientesUtilsTest.criaCriarClienteDto();

        when(gateway.buscarPorCpf("234.524.443-23")).thenReturn(null);

        Cliente clienteCriado = ClientesUtilsTest.criaCliente();

        when(gateway.criar(any(Cliente.class))).thenReturn(clienteCriado);

        ClienteDTO resultado = clienteController.criarCliente(clienteDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Jorge", resultado.nome());
        assertEquals("123.456.789-09", resultado.cpf());
        assertEquals("07/12/2015", resultado.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.endereco());
        assertEquals("(99) 99999-9999", resultado.numeroContato());
        assertEquals("email@email.com", resultado.email());
        assertEquals("Cartao de Credito", resultado.infoPagamento());
        verify(gateway).criar(any(Cliente.class));
    }

    @Test
    public void deveRetornarClientePorId() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorId(cliente.getId())).thenReturn(cliente);

        ClienteDTO resultado = clienteController.buscarClientePorId(cliente.getId());

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Jorge", resultado.nome());
        assertEquals("123.456.789-09", resultado.cpf());
        assertEquals("07/12/2015", resultado.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.endereco());
        assertEquals("(99) 99999-9999", resultado.numeroContato());
        assertEquals("email@email.com", resultado.email());
        assertEquals("Cartao de Credito", resultado.infoPagamento());
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

        ClienteDTO resultado = clienteController.atualizarCliente(clienteDTO);

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
    public void deveRetornarClientePorCpf() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorCpf(cliente.getCpf())).thenReturn(cliente);

        ClienteDTO resultado = clienteController.buscarClientePorCpf(cliente.getCpf());

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Jorge", resultado.nome());
        assertEquals("123.456.789-09", resultado.cpf());
        assertEquals("07/12/2015", resultado.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.endereco());
        assertEquals("(99) 99999-9999", resultado.numeroContato());
        assertEquals("email@email.com", resultado.email());
        assertEquals("Cartao de Credito", resultado.infoPagamento());
    }

    @Test
    public void deveRetornarClientePorNome() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorNome(cliente.getNome())).thenReturn(cliente);

        ClienteDTO resultado = clienteController.buscarClientePorNome(cliente.getNome());

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Jorge", resultado.nome());
        assertEquals("123.456.789-09", resultado.cpf());
        assertEquals("07/12/2015", resultado.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.endereco());
        assertEquals("(99) 99999-9999", resultado.numeroContato());
        assertEquals("email@email.com", resultado.email());
        assertEquals("Cartao de Credito", resultado.infoPagamento());
    }

    @Test
    public void deveDeletarClienteSeExistir() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorId(cliente.getId())).thenReturn(cliente);

        clienteController.removerCliente(cliente.getId());

        verify(gateway).remover(cliente.getId());
    }

    @Test
    public void deveRetornarListaClientes() {
        Cliente cliente1 = new Cliente(1L, "Jorge Silva Sauro", "123.456.789-09", "07/12/2015", "Rua Teste, 123, Bairro XYZ", "(99) 99999-9999", "email@email.com", "Cartao de Credito");
        Cliente cliente2 = new Cliente(2L, "Jonas", "776.311.789-29", "01/02/2015", "Rua Teste, 123, Bairro XYZ", "(99) 99999-9999", "email@email.com", "Cartao de Credito");

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(cliente1);
        listaClientes.add(cliente2);

        when(gateway.listarTodos()).thenReturn(listaClientes);

        List<ClienteDTO> resultado = clienteController.listarTodosClientes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(cliente1.getId(), resultado.get(0).id());
        assertEquals(cliente2.getId(), resultado.get(1).id());

        verify(gateway, times(1)).listarTodos();
    }
}