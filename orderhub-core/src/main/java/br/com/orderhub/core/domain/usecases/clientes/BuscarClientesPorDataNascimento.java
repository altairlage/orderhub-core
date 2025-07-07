package br.com.orderhub.core.domain.usecases.clientes;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;

import java.util.List;

public class BuscarClientesPorDataNascimento {
    private final IClienteGateway gateway;

    public BuscarClientesPorDataNascimento(IClienteGateway gateway) {
        this.gateway = gateway;
    }

    public List<ClienteDTO> run(String dataNascimento){
        return ClientePresenter.ToListDTO(gateway.buscarPorDataNascimento(dataNascimento));

    }
}
