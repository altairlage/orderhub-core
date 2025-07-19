package br.com.orderhub.core.exceptions;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String sku, int solicitado, int disponivel) {
        super("Estoque insuficiente para SKU: " + sku +
              ". Solicitado: " + solicitado + ", Disponível: " + disponivel);
    }
}
