package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;

public class AtualizarCliente {
    private final IClienteGateway gateway;

    public AtualizarCliente(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO run(ClienteDTO clienteAtualizado) {
        Cliente clienteAntigo = gateway.buscarPorCpf(clienteAtualizado.cpf());

        if (clienteAntigo == null) {
            throw new ClienteNaoEncontradoException("Cliente com cpf: " + clienteAtualizado.cpf() + " não encontrado.");
        }
        return ClientePresenter.ToDTO(gateway.atualizar(ClientePresenter.ToDomain(clienteAtualizado)));
    }
}
