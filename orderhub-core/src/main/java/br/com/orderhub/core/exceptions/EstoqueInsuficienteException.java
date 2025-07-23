package br.com.orderhub.core.exceptions;

public class EstoqueInsuficienteException extends OrderhubException {
    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}