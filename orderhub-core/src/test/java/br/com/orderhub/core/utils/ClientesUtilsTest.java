package br.com.orderhub.core.utils;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;

public abstract class ClientesUtilsTest {

    public static Cliente criaCliente() {
        return new Cliente(
                1L,
                "Jorge",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );
    }

    public static ClienteDTO criaClienteDto() {
        return new ClienteDTO(
                1L,
                "Jorge Silva Sauro",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );
    }

    public static CriarClienteDTO criaCriarClienteDto() {
        return new CriarClienteDTO(
                "Jorge",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );
    }
}
