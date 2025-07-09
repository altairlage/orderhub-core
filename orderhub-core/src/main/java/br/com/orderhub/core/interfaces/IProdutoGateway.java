package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Produto;

import java.util.List;

public interface IProdutoGateway {
    Produto buscarPorId(Long id);
    Produto buscarPorNome(String nome);
    Produto criar(Produto produto);
    Produto atualizar(Produto produto);
    void deletar(Long id);
    List<Produto> listarTodos();
}
