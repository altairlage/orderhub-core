package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.domain.usecases.estoques.*;
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstoqueController {

    private final BaixarEstoque baixarEstoque;
    private final ReporEstoque reporEstoque;
    private final ConsultarEstoquePorId consultarEstoquePorId;
    private final BaixarEstoqueMultiplo baixarEstoqueMultiplo;

    public EstoqueController(IEstoqueGateway estoqueGateway) {
        this.baixarEstoque = new BaixarEstoque(estoqueGateway);
        this.reporEstoque = new ReporEstoque(estoqueGateway);
        this.consultarEstoquePorId = new ConsultarEstoquePorId(estoqueGateway);
        this.baixarEstoqueMultiplo = new BaixarEstoqueMultiplo(estoqueGateway);
    }

    public Estoque baixar(Long id, int quantidade) {
        baixarEstoque.executar(id, quantidade);
        return consultarPorId(id);
    }

    public Estoque repor(Long id, int quantidade) {
        reporEstoque.executar(id, quantidade);
        return consultarPorId(id);
    }

    public Estoque consultarPorId(Long id) {
        return consultarEstoquePorId.run(id)
                .orElseThrow(() -> new EstoqueNaoEncontradoException("Estoque não encontrado para o ID: " + id));
    }

    public void baixarEstoquePorPedido(PedidoDTO dto) {
        List<ItemEstoqueDTO> itensParaBaixa = dto.listaQtdProdutos().stream()
                .map(itemMap -> {
                    ProdutoDTO produto = (ProdutoDTO) itemMap.get("produto");
                    Integer quantidade = (Integer) itemMap.get("quantidade");
                    return new ItemEstoqueDTO(produto, quantidade);
                })
                .collect(Collectors.toList());

        baixarEstoqueMultiplo.executar(itensParaBaixa);
    }

//    public void baixarEstoquePorPedido(PedidoDTO dto) {
//        List<ItemEstoqueDTO> itensParaBaixa = dto.listaQtdProdutos().stream()
//            .flatMap(map -> map.entrySet().stream())
//            .map(entry -> new ItemEstoqueDTO(
//                (ProdutoDTO) entry.getValue(),
//                    Integer.parseInt(entry.getKey())
//            ))
//            .collect(Collectors.toList());
//
//        baixarEstoqueMultiplo.executar(itensParaBaixa);
//    }
    
    public void baixarEstoqueMultiplo(List<ItemEstoqueDTO> itens) {
        baixarEstoqueMultiplo.executar(itens);
    }
}