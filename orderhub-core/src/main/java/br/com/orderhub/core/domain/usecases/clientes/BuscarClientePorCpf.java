package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.InputStringValidator;

public class BuscarClientePorCpf {
    private final IClienteGateway clienteGateway;

    public BuscarClientePorCpf(IClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteDTO run(String cpf) {
        if (!InputStringValidator.isValidCpf(cpf)) {
            throw new IllegalArgumentException("CPF invalido");
        }

        final Cliente cliente = clienteGateway.buscarPorCpf(cpf);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com cpf: " + cpf + " nao encontrado");
        }
        return ClientePresenter.ToDTO(cliente);
    }
}
