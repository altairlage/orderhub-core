package br.com.orderhub.core.domain.usecases.produtos;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.interfaces.IProdutoGateway;

import java.util.List;

public class ListarTodosProdutos {
    private final IProdutoGateway gateway;

    public ListarTodosProdutos(IProdutoGateway gateway) {
        this.gateway = gateway;
    }

    public List<Produto> run(){
        return gateway.listarTodos();
    }

}
