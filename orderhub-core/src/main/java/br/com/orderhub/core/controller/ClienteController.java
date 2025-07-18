package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.presenters.ClientePresenter;
import br.com.orderhub.core.domain.usecases.clientes.*;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;

import java.util.List;

public class ClienteController {
    private final IClienteGateway gateway;
    private final AtualizarCliente atualizarCliente;
    private final BuscarClientePorId buscarClientePorId;
    private final BuscarClientePorCpf buscarClientePorCpf;
    private final BuscarClientePorNome buscarClientePorNome;
    private final CriarCliente criarCliente;
    private final RemoverCliente removerCliente;
    private final ListarTodosClientes listarTodosClientes;

    public ClienteController(IClienteGateway gateway) {
        this.gateway = gateway;
        this.atualizarCliente = new AtualizarCliente(gateway);
        this.buscarClientePorId = new BuscarClientePorId(gateway);
        this.buscarClientePorCpf = new BuscarClientePorCpf(gateway);
        this.buscarClientePorNome = new BuscarClientePorNome(gateway);
        this.criarCliente = new CriarCliente(gateway);
        this.removerCliente = new RemoverCliente(gateway);
        this.listarTodosClientes = new ListarTodosClientes(gateway);
    }

    public ClienteDTO criarCliente(CriarClienteDTO criarClienteDTO) {
        return ClientePresenter.ToDTO(this.criarCliente.run(criarClienteDTO));
    }

    public ClienteDTO atualizarCliente(ClienteDTO clienteDTO) {
        return this.atualizarCliente.run(clienteDTO);
    }

    public void removerCliente(Long id) {
        this.removerCliente.run(id);
    }

    public ClienteDTO buscarClientePorId(Long id) {
        return this.buscarClientePorId.run(id);
    }

    public ClienteDTO buscarClientePorCpf(String cpf) {
        return this.buscarClientePorCpf.run(cpf);
    }
    public ClienteDTO buscarClientePorNome(String nome) {
        return this.buscarClientePorNome.run(nome);
    }

    public List<ClienteDTO> listarTodosClientes(){
        return this.listarTodosClientes.run();
    }
}
