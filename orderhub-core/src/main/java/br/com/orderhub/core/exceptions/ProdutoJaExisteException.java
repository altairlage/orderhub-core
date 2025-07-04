package br.com.orderhub.core.exceptions;

public class ProdutoJaExisteException extends RuntimeException {
    public ProdutoJaExisteException(String message) {
        super(message);
    }
}
