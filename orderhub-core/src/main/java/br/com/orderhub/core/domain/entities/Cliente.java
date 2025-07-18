package br.com.orderhub.core.domain.entities;

import br.com.orderhub.core.interfaces.InputStringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String numeroContato;
    private String email;
    private String infoPagamento;

    public Cliente(Long id, String nome, String cpf, String dataNascimento, String endereco, String numeroContato, String email, String infoPagamento) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNascimento(dataNascimento);
        this.setEndereco(endereco);
        this.setNumeroContato(numeroContato);
        this.setEmail(email);
        this.setInfoPagamento(infoPagamento);
    }

    public Cliente(String nome, String cpf, String dataNascimento, String endereco, String numeroContato, String email, String infoPagamento) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNascimento(dataNascimento);
        this.setEndereco(endereco);
        this.setNumeroContato(numeroContato);
        this.setEmail(email);
        this.setInfoPagamento(infoPagamento);
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID vazio ou inválido");
        }
        this.id = id;
    }

    public void setDataNascimento(String dataNascimento) {
        if (!InputStringValidator.isValidDate(dataNascimento)) {
            throw new IllegalArgumentException("Datas devem estar no padrão DD/MM/YYYY");
        }
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email vazio ou inválido");
        }

        if (!InputStringValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email deve estar no padrão xxx@email.com ou yyyy@email.com.br");
        }

        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        if(cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF vazio ou invalido");
        }

        if(InputStringValidator.isValidCpf(cpf)) {
            this.cpf = cpf;
        }
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNumeroContato(String numeroContato) {
        if (!InputStringValidator.isValidTelephone(numeroContato))
            throw new IllegalArgumentException("Número de contato deve estar no padrão (99) 99999-9999");

        this.numeroContato = numeroContato;
    }

    public void setInfoPagamento(String infoPagamento) {
        this.infoPagamento = infoPagamento;
    }
}
