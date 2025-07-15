package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.InputStringValidator;

public class BuscarClientePorEmail {
    private final IClienteGateway clienteGateway;

    public BuscarClientePorEmail(IClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteDTO run(String email) {
        if (!InputStringValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email invalido");
        }

        final Cliente cliente = clienteGateway.buscarPorEmail(email);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com email " + email + " nao encontrado");
        }
        return ClientePresenter.ToDTO(cliente);
    }
}
