package br.com.orderhub.core.interfaces;

import java.util.List; // Importe a classe List
import java.util.Optional;
import br.com.orderhub.core.domain.entities.Estoque;

public interface IEstoqueGateway {

    /**
     * Busca um único item de estoque pelo seu SKU.
     */
    Optional<Estoque> buscarPorSku(String sku);

    /**
     * Busca uma lista de itens de estoque a partir de uma lista de SKUs.
     * Este método é essencial para otimizar operações em lote.
     */
    List<Estoque> buscarPorSkus(List<String> skus); // <-- MÉTODO QUE FALTAVA

    /**
     * Salva o estado de um item de estoque, seja para criação ou atualização.
     */
    void salvar(Estoque estoque);
}