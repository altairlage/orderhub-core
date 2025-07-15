package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.InputStringValidator;

public class BuscarClientePorNome {
    private final IClienteGateway clienteGateway;

    public BuscarClientePorNome(IClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteDTO run(String nome) {
        final Cliente cliente = clienteGateway.buscarPorNome(nome);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente nao encontrado");
        }
        return ClientePresenter.ToDTO(cliente);
    }
}
