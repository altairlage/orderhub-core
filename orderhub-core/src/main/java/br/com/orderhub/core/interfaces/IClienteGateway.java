package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Cliente;

import java.util.List;

public interface IClienteGateway {
    // Usar Optional para evitar retornos nulos
    Cliente buscarPorId(Long id);
    Cliente buscarPorCpf(String cpf);
    Cliente buscarPorNome(String nome);
    Cliente criar(Cliente cliente);
    Cliente atualizar(Cliente cliente);
    void remover(Long id);
    List<Cliente> listarTodos();
}
