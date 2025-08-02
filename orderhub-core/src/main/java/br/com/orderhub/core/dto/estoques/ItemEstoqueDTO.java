package br.com.orderhub.core.dto.estoques;

import br.com.orderhub.core.dto.produtos.ProdutoDTO;

// DTO para representar um item em uma operação de estoque em lote.
public record ItemEstoqueDTO(ProdutoDTO produto, int quantidade) {
}