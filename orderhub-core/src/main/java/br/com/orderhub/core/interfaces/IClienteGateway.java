package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Cliente;

import java.util.List;

public interface IClienteGateway {
    Cliente buscarPorId(Long id);
    List<Cliente> buscarPorDataNascimento(String dataNascimento);
    Cliente criar(Cliente cliente);
    Cliente atualizar(Cliente clienteAntigo, Cliente clienteAtualizado);
    void remover(Long id);
}
