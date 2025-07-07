package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;

public class CriarCliente {
    private final IClienteGateway gateway;

    public CriarCliente(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO run(ClienteDTO clienteDTO) {
        return ClientePresenter.ToDTO(gateway.criar(ClientePresenter.ToDomain(clienteDTO)));
    }
}
