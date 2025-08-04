package br.com.orderhub.core.exceptions;

public class ProdutoJaCadastradoNoEstoqueException extends OrderhubException {
    public ProdutoJaCadastradoNoEstoqueException(String message) {
        super(message);
    }
}
