package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;

public class RemoverCliente {
    private final IClienteGateway gateway;

    public RemoverCliente(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public void run(Long id) {
        Cliente cliente = gateway.buscarPorId(id);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " nao foi encontrado");
        }
        gateway.remover(id);
    }
}
