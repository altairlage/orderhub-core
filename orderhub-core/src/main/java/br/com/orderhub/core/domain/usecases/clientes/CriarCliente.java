package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import br.com.orderhub.core.exceptions.CpfJaCadastradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;

public class CriarCliente {
    private final IClienteGateway gateway;

    public CriarCliente(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public Cliente run(CriarClienteDTO clienteDTO) {
        final Cliente clienteExistente = gateway.buscarPorCpf(clienteDTO.cpf());

        if(clienteExistente != null) {
            throw new CpfJaCadastradoException("O CPF " + clienteDTO.cpf() + " ja esta sendo usado em outro cadastro.");
        }

        final Cliente novoCliente = new Cliente(clienteDTO.nome(), clienteDTO.cpf(), clienteDTO.dataNascimento(), clienteDTO.endereco(), clienteDTO.numeroContato(), clienteDTO.email(), clienteDTO.infoPagamento());
        return gateway.criar(novoCliente);
    }
}
