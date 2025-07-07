package br.com.orderhub.core.exceptions;

public class OrderhubException extends RuntimeException {
    public OrderhubException(String message) {
        super(message);
    }
}
