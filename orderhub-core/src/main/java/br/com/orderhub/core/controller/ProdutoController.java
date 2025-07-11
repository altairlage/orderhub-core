package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.presenters.ProdutoPresenter;
import br.com.orderhub.core.domain.usecases.*;
import br.com.orderhub.core.dto.CriarProdutoDTO;
import br.com.orderhub.core.dto.ProdutoDTO;
import br.com.orderhub.core.interfaces.IProdutoGateway;

public class ProdutoController {
    private IProdutoGateway gateway;
    private BuscarProdutoPorId buscarProdutoPorId;
    private BuscarProdutoPorNome buscarProdutoPorNome;
    private CriarProduto criarProduto;
    private EditarProduto editarProduto;
    private DeletarProduto deletarProduto;

    public ProdutoController(IProdutoGateway gateway) {
        this.gateway = gateway;
        buscarProdutoPorId = new BuscarProdutoPorId(this.gateway);
        buscarProdutoPorNome = new BuscarProdutoPorNome(this.gateway);
        criarProduto = new CriarProduto(this.gateway);
        editarProduto = new EditarProduto(this.gateway);
        deletarProduto = new DeletarProduto(this.gateway);
    }

    /*
    * TIRAR DUVIDA COM O PROFESSOR:
    * preciso colocar throws para as exceptions no controller tbm?
    * Devo tratar aqui a exception com um try catch e retornar null ou coisa do tipo quando tiver erro?
    * Ou devo tratar a exception no controller / service da API?
    *
    * PARA QUEM ESTIVER LENDO DO GRUPO:
    * Vou considerar que trataremos as exceptions no controller ou service da API.
    * De acordo com o que eu li, as camadas externas (como o controller da API) podem conhecer e depender dos
    * elementos do core, como as exceptions, mas nao o contrario.
    */

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Produto produto = this.buscarProdutoPorId.run(id);
        return ProdutoPresenter.ToDTO(produto);
    }

    public ProdutoDTO buscarProdutoPorNome(String nome) {
        Produto produto = this.buscarProdutoPorNome.run(nome);
        return ProdutoPresenter.ToDTO(produto);
    }

    public ProdutoDTO criarProduto(CriarProdutoDTO criarProdutoDTO) {
        return ProdutoPresenter.ToDTO(this.criarProduto.run(criarProdutoDTO));
    }

    public ProdutoDTO editarProduto(ProdutoDTO editarProdutoDTO) {
        return ProdutoPresenter.ToDTO(this.editarProduto.run(editarProdutoDTO));
    }

    public void deletarProduto(Long id) {
        this.deletarProduto.run(id);
    }


    // implementar os metodos
}
