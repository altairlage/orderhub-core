package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.presenters.EstoquePresenter;
import br.com.orderhub.core.domain.usecases.estoques.*;
import br.com.orderhub.core.dto.estoques.AtualizarEstoqueDTO;
import br.com.orderhub.core.dto.estoques.EstoqueDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

public class EstoqueController {

    private final IEstoqueGateway estoqueGateway;
    private final AdicionarProdutoNoEstoque adicionarProdutoNoEstoque;
    private final BaixarEstoque baixarEstoque;
    private final ReporEstoque reporEstoque;
    private final ConsultarEstoquePorIdProduto consultarEstoquePorIdProduto;
    private final RemoverProdutoNoEstoque removerProdutoNoEstoque;

    public EstoqueController(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
        this.adicionarProdutoNoEstoque = new AdicionarProdutoNoEstoque(estoqueGateway);
        this.baixarEstoque = new BaixarEstoque(estoqueGateway);
        this.reporEstoque = new ReporEstoque(estoqueGateway);
        this.consultarEstoquePorIdProduto = new ConsultarEstoquePorIdProduto(estoqueGateway);
        this.removerProdutoNoEstoque = new RemoverProdutoNoEstoque(estoqueGateway);
    }

    public EstoqueDTO baixarEstoque(AtualizarEstoqueDTO estoqueDTO) throws EstoqueNaoEncontradoException {
        return EstoquePresenter.toDTO(baixarEstoque.run(EstoquePresenter.atualizarDtoToDomain(estoqueDTO)));
    }

    public EstoqueDTO reporEstoque(AtualizarEstoqueDTO estoqueDTO) throws EstoqueNaoEncontradoException {
        return EstoquePresenter.toDTO(reporEstoque.run(EstoquePresenter.atualizarDtoToDomain(estoqueDTO)));
    }

    public EstoqueDTO consultarEstoquePorIdProduto(Long idProduto) throws EstoqueNaoEncontradoException {
        return EstoquePresenter.toDTO(consultarEstoquePorIdProduto.run(idProduto));
    }

    public EstoqueDTO adicionarProdutoNoEstoque(AtualizarEstoqueDTO estoqueDTO) throws EstoqueNaoEncontradoException {
        return EstoquePresenter.toDTO(adicionarProdutoNoEstoque.run(EstoquePresenter.atualizarDtoToDomain(estoqueDTO)));
    }

    public void removerProdutoNoEstoque(Long idProduto) throws EstoqueNaoEncontradoException {
        removerProdutoNoEstoque.run(idProduto);
    }
}