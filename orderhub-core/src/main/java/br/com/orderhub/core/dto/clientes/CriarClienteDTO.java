package br.com.orderhub.core.dto.clientes;

public record CriarClienteDTO(
        String nome,
        String cpf,
        String dataNascimento,
        String endereco,
        String numeroContato,
        String email,
        String infoPagamento
) {
}
