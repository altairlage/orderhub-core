package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.domain.usecases.estoques.*; // Importar todos
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO; // Importar novo DTO
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

import java.util.List; // Importar
import java.util.Map;
import java.util.stream.Collectors; // Importar

public class EstoqueController {

    private final BaixarEstoque baixarEstoque;
    private final ReporEstoque reporEstoque;
    private final ConsultarEstoquePorSku consultarEstoquePorSku;
    private final BaixarEstoqueMultiplo baixarEstoqueMultiplo; // Adicionar novo caso de uso

    // O construtor ideal recebe o Gateway e instancia os casos de uso.
    // Isso simplifica a injeção de dependência no service.
    public EstoqueController(IEstoqueGateway estoqueGateway) {
        this.baixarEstoque = new BaixarEstoque(estoqueGateway);
        this.reporEstoque = new ReporEstoque(estoqueGateway);
        this.consultarEstoquePorSku = new ConsultarEstoquePorSku(estoqueGateway);
        this.baixarEstoqueMultiplo = new BaixarEstoqueMultiplo(estoqueGateway);
    }
    
    // ... métodos baixar, repor, consultarPorSku permanecem os mesmos ...

    public Estoque baixar(String sku, int quantidade) {
        baixarEstoque.executar(sku, quantidade);
        return consultarPorSku(sku);
    }
    
    public Estoque repor(String sku, int quantidade) {
        reporEstoque.executar(sku, quantidade);
        return consultarPorSku(sku);
    }

    public Estoque consultarPorSku(String sku) {
        return consultarEstoquePorSku.run(sku)
                .orElseThrow(() -> new EstoqueNaoEncontradoException("Estoque não encontrado para o SKU: " + sku));
    }

    // MÉTODO REATORADO USANDO A LÓGICA ATÔMICA
    public void baixarEstoquePorPedido(PedidoDTO dto) {
        // Converte a lista de produtos do pedido para a lista do nosso novo DTO
        List<ItemEstoqueDTO> itensParaBaixa = dto.listaQtdProdutos().stream()
            .flatMap(map -> map.entrySet().stream())
            .map(entry -> new ItemEstoqueDTO(
                String.valueOf(entry.getValue().id()), // AQUI FICA A "GAMBIARRA" ID -> SKU
                entry.getKey() // Quantidade
            ))
            .collect(Collectors.toList());
        
        // Chama o caso de uso atômico
        baixarEstoqueMultiplo.executar(itensParaBaixa);
    }
    
    // Novo método para o endpoint /baixar-multiplos do service
    public void baixarEstoqueMultiplo(List<ItemEstoqueDTO> itens) {
        baixarEstoqueMultiplo.executar(itens);
    }
}