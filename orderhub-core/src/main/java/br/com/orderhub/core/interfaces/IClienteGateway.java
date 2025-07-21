package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Cliente;

import java.util.List;

public interface IClienteGateway {
    Cliente buscarPorId(Long id);
    Cliente buscarPorCpf(String cpf);
    Cliente buscarPorNome(String nome);
    Cliente criar(Cliente cliente);
    Cliente atualizar(Cliente cliente);
    void remover(Long id);
    List<Cliente> listarTodos();
}
