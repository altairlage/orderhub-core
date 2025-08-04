package br.com.orderhub.core.interfaces;

import br.com.orderhub.core.domain.entities.Estoque;

public interface IEstoqueGateway {
    Estoque adicionarProdutoNoEstoque(Estoque estoque);
    void removerProdutoNoEstoque(Long id);
    Estoque consultarEstoquePorIdProduto(Long id);
    Estoque baixarEstoque(Estoque estoque);
    Estoque reporEstoque(Estoque estoque);
}