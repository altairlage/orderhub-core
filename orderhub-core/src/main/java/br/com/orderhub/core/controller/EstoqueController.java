package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoque;
import br.com.orderhub.core.domain.usecases.estoques.ConsultarEstoquePorSku;
import br.com.orderhub.core.domain.usecases.estoques.ReporEstoque;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;

import java.util.Map;

public class EstoqueController {

    private final BaixarEstoque baixarEstoque;
    private final ReporEstoque reporEstoque;
    private final ConsultarEstoquePorSku consultarEstoquePorSku;

    public EstoqueController(BaixarEstoque baixarEstoque,
            ReporEstoque reporEstoque,
            ConsultarEstoquePorSku consultarEstoquePorSku) {
        this.baixarEstoque = baixarEstoque;
        this.reporEstoque = reporEstoque;
        this.consultarEstoquePorSku = consultarEstoquePorSku;
    }

    public Estoque baixar(String sku, int quantidade) {
        baixarEstoque.executar(sku, quantidade);
        return consultarEstoquePorSku.run(sku)
                .orElseThrow(() -> new EstoqueNaoEncontradoException("Estoque não encontrado para o SKU: " + sku));
    }

    public Estoque repor(String sku, int quantidade) {
        reporEstoque.executar(sku, quantidade);
        return consultarEstoquePorSku.run(sku)
                .orElseThrow(() -> new EstoqueNaoEncontradoException("Estoque não encontrado para o SKU: " + sku));
    }

    public Estoque consultarPorSku(String sku) {
        return consultarEstoquePorSku.run(sku)
                .orElseThrow(() -> new EstoqueNaoEncontradoException("Estoque não encontrado para o SKU: " + sku));
    }

    public void baixarEstoquePorPedido(PedidoDTO dto) {
        for (Map<Integer, ProdutoDTO> item : dto.listaQtdProdutos()) {
            for (Map.Entry<Integer, ProdutoDTO> entry : item.entrySet()) {
                int quantidade = entry.getKey();
                Long id = entry.getValue().id(); // Usando o ID como identificador
                baixar(String.valueOf(id), quantidade); // Convertendo o ID para string
            }
        }
    }
}
