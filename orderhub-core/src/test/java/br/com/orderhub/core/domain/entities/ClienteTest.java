package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.utils.ClientesUtilsTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    public void deveCriarCliente() {
        Cliente cliente = ClientesUtilsTest.criaCliente();

        assertEquals(1L, cliente.getId());
        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789-09", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());
    }

    @Test
    public void deveCompararClientesPorEquals() {
        Cliente cliente1 = ClientesUtilsTest.criaCliente();
        Cliente cliente2 = ClientesUtilsTest.criaCliente();

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    public void deveGerarHashcodeConsistente() {
        Cliente cliente1 = ClientesUtilsTest.criaCliente();
        Cliente cliente2 = ClientesUtilsTest.criaCliente();

        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }
}