package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;

public class BuscarClientePorId {
    private final IClienteGateway gateway;

    public BuscarClientePorId(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public ClienteDTO run(Long idCliente) {
        Cliente cliente = gateway.buscarPorId(idCliente);
        if(cliente == null){
            throw new ClienteNaoEncontradoException("Cliente com ID " + idCliente + " nao foi encontrado");
        }
        return ClientePresenter.ToDTO(cliente);
    }
}
