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

        // Pega todos os SKUs para uma busca otimizada
        List<String> skus = itensParaBaixa.stream().map(ItemEstoqueDTO::sku).collect(Collectors.toList());

        // Busca todos os estoques de uma só vez (usando o método sugerido no gateway)
        Map<String, Estoque> estoquesEncontrados = estoqueGateway.buscarPorSkus(skus)
                .stream()
                .collect(Collectors.toMap(Estoque::getSku, Function.identity()));

        // ETAPA 1: VERIFICAR TUDO ANTES DE MODIFICAR
        for (ItemEstoqueDTO item : itensParaBaixa) {
            Estoque estoque = estoquesEncontrados.get(item.sku());
            if (estoque == null) {
                throw new EstoqueNaoEncontradoException("Estoque não encontrado para o SKU (produto ID): " + item.sku());
            }
            if (estoque.getQuantidadeDisponivel() < item.quantidade()) {
                throw new EstoqueInsuficienteException(
                    String.format("Estoque insuficiente para SKU %s. Solicitado: %d, Disponível: %d",
                        item.sku(), item.quantidade(), estoque.getQuantidadeDisponivel())
                );
            }
        }

        // ETAPA 2: EXECUTAR AS BAIXAS (somente se todas as verificações passaram)
        for (ItemEstoqueDTO item : itensParaBaixa) {
            Estoque estoque = estoquesEncontrados.get(item.sku());
            estoque.baixarEstoque(item.quantidade());
            estoqueGateway.salvar(estoque);
        }
    }
}