package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import br.com.orderhub.core.exceptions.CpfJaCadastradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.utils.ClientesUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarClienteTest {
    private IClienteGateway gateway;
    private CriarCliente criarCliente;

    @BeforeEach
    public void setup() {
        gateway = mock(IClienteGateway.class);
        criarCliente = new CriarCliente(gateway);
    }

    @Test
    public void deveCriarClienteCasoNaoExista() {
        CriarClienteDTO clienteDTO = ClientesUtilsTest.criaCriarClienteDto();

        when(gateway.buscarPorCpf("234.524.443-23")).thenReturn(null);

        Cliente clienteCriado = ClientesUtilsTest.criaCliente();

        when(gateway.criar(any(Cliente.class))).thenReturn(clienteCriado);

        Cliente resultado = criarCliente.run(clienteDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Jorge", resultado.getNome());
        assertEquals("123.456.789-09", resultado.getCpf());
        assertEquals("07/12/2015", resultado.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.getEndereco());
        assertEquals("(99) 99999-9999", resultado.getNumeroContato());
        assertEquals("email@email.com", resultado.getEmail());
        assertEquals("Cartao de Credito", resultado.getInfoPagamento());
        verify(gateway).criar(any(Cliente.class));
    }

    @Test
    public void deveLancarExceptionSeClienteJaECadastrado() {
        CriarClienteDTO clienteDTO = ClientesUtilsTest.criaCriarClienteDto();
        Cliente clienteCadastrado = ClientesUtilsTest.criaCliente();

        when(gateway.buscarPorCpf("123.456.789-09")).thenReturn(clienteCadastrado);

        assertThrows(CpfJaCadastradoException.class, () -> criarCliente.run(clienteDTO));
        verify(gateway, never()).criar(any());
    }
}