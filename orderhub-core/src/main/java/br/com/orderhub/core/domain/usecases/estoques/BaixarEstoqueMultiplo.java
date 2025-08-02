package br.com.orderhub.core.domain.usecases.estoques;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO;
import br.com.orderhub.core.exceptions.EstoqueInsuficienteException;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaixarEstoqueMultiplo {

    private final IEstoqueGateway estoqueGateway;

    public BaixarEstoqueMultiplo(IEstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public void executar(List<ItemEstoqueDTO> itensParaBaixa) {
        if (itensParaBaixa == null || itensParaBaixa.isEmpty()) {
            return;
        }

        // Busca os IDs dos produtos a partir do ProdutoDTO
        List<Long> ids = itensParaBaixa.stream()
                .map(item -> item.produto().id())
                .collect(Collectors.toList());

        Map<Long, Estoque> estoquesEncontrados = estoqueGateway.buscarPorIds(ids)
                .stream()
                .collect(Collectors.toMap(Estoque::getId, Function.identity()));

        for (ItemEstoqueDTO item : itensParaBaixa) {
            Estoque estoque = estoquesEncontrados.get(item.produto().id());
            if (estoque == null) {
                // Mensagem de erro mais clara
                throw new EstoqueNaoEncontradoException("Estoque não encontrado para o produto: " + item.produto().nome() + " (ID: " + item.produto().id() + ")");
            }
            if (estoque.getQuantidadeDisponivel() < item.quantidade()) {
                // Mensagem de erro mais clara
                throw new EstoqueInsuficienteException(
                    String.format("Estoque insuficiente para o produto %s (ID: %d). Solicitado: %d, Disponível: %d",
                        item.produto().nome(), item.produto().id(), item.quantidade(), estoque.getQuantidadeDisponivel())
                );
            }
        }

        for (ItemEstoqueDTO item : itensParaBaixa) {
            Estoque estoque = estoquesEncontrados.get(item.produto().id());
            estoque.baixarEstoque(item.quantidade());
            estoqueGateway.salvar(estoque);
        }
    }
}