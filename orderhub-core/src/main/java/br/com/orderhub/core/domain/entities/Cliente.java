package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.interfaces.InputValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Cliente {
    private Long id;
    private String nome;
    private String dataNascimento;
    private String endereco;
    private String numeroContato;
    private String email;
    private String infoPagamento;

    public Cliente(Long id, String nome, String dataNascimento, String endereco, String numeroContato, String email, String infoPagamento) {
        this.setId(id);
        this.setNome(nome);
        this.setDataNascimento(dataNascimento);
        this.setEndereco(endereco);
        this.setNumeroContato(numeroContato);
        this.setEmail(email);
        this.setInfoPagamento(infoPagamento);
    }

    public void setId(Long id) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("ID vazio ou inválido");
        }
        this.id = id;
    }

    public void setDataNascimento(String dataNascimento) {
        if (InputValidator.isValidDate(dataNascimento)) {
            throw new IllegalArgumentException("Datas devem estar no padrão DD/MM/YYYY");
        }
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        if (email != null && email.trim().length() > 0) {
            throw new IllegalArgumentException("Email vazio ou inválido");
        }
        if (InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email deve estar no padrão xxx@email.com ou yyyy@email.com.br");
        }

        this.email = email;
    }
}
