package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.usecases.*;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProdutoController {
    private IProdutoGateway gateway;
    private BuscarProdutoPorId buscarProdutoPorId;
    private BuscarProdutoPorNome buscarProdutoPorNome;
    private CriarProduto criarProduto;
    private EditarProduto editarProduto;
    private DeletarProduto deletarProduto;

    // implementar os metodos
}
