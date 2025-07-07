package br.com.orderhub.core.exceptions;

public class ProdutoNaoEncontradoException extends OrderhubException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
