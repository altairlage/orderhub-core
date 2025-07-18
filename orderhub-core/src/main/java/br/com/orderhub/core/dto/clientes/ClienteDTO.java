package br.com.orderhub.core.dto.clientes;

public record ClienteDTO(Long id, String nome, String cpf, String dataNascimento, String endereco, String numeroContato, String email, String infoPagamento) {
}
