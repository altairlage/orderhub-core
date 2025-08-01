package br.com.orderhub.core.dto.estoques;

// DTO para representar um item em uma operação de estoque em lote.
public record ItemEstoqueDTO(Long id, int quantidade) {
}