package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.exceptions.ProdutoJaCadastradoNoEstoqueException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class AdicionarProdutoNoEstoque {
    private final IEstoqueGateway estoqueGateway;

    public AdicionarProdutoNoEstoque(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public Estoque run(Estoque estoqueDTO) {
        if (estoqueDTO.getQuantidadeDisponivel() == null || estoqueDTO.getQuantidadeDisponivel() < 0) {
            throw new IllegalArgumentException("Quantidade inicial de produto no estoque nula ou inválida");
        }

        Estoque verificacao = estoqueGateway.consultarEstoquePorIdProduto(estoqueDTO.getIdProduto());

        if (verificacao != null) {
            throw new ProdutoJaCadastradoNoEstoqueException("Produto de ID " + estoqueDTO.getIdProduto() + " já existe no estoque");
        }

        return estoqueGateway.adicionarProdutoNoEstoque(estoqueDTO);
    }
}
