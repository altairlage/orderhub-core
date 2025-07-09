package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;

import java.util.List;

public class ListarTodosClientes {
    private final IClienteGateway gateway;

    public ListarTodosClientes(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<ClienteDTO> run(){
        return ClientePresenter.ToListDTO(gateway.listarTodos());
    }
}
