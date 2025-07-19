package br.com.orderhub.core.exceptions;

public class EstoqueNaoEncontradoException extends RuntimeException {

    public EstoqueNaoEncontradoException(String sku) {
        super("Estoque não encontrado para o SKU: " + sku);
    }
}
