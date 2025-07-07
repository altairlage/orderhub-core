package br.com.orderhub.core.exceptions;

public class ProdutoJaExisteException extends OrderhubException {
    public ProdutoJaExisteException(String message) {
        super(message);
    }
}
