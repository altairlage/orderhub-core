package br.com.orderhub.core.exceptions;

public class EstoqueNaoEncontradoException extends OrderhubException {
    public EstoqueNaoEncontradoException(String message) {
        super(message);
    }
}