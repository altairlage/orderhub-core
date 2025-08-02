package br.com.orderhub.core.domain.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {

    private Long id;
    private Integer quantidadeDisponivel;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public void baixarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para baixa deve ser maior que zero.");
        }

        if (this.quantidadeDisponivel < quantidade) {
            throw new IllegalStateException("Estoque insuficiente para a operação.");
        }

        this.quantidadeDisponivel -= quantidade;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void reporEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para reposição deve ser maior que zero.");
        }

        this.quantidadeDisponivel += quantidade;
        this.atualizadoEm = LocalDateTime.now();
    }
}
