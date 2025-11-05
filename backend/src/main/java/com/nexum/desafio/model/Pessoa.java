package com.nexum.desafio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Pessoa {
    // Identificador único da entidade.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome da pessoa, com restrição de não ser nulo/vazio.
    @NotBlank
    private String nome;

    // CPF da pessoa, com restrição de não ser nulo/vazio e de ter 11 dígitos numéricos.
    @NotBlank
    @Pattern(regexp = "\\d{11}")
    private String cpf;

    // Campo de telefone da pessoa.
    private String telefone;

    // Campo de e-mail da pessoa, com restrições de não ser nulo/vazio e de formato válido.
    @NotBlank
    @Email
    private String email;

    // Objeto embutido para mapear os dados de endereço.
    @Embedded
    private Endereco endereco;

    // --- Métodos Getters e Setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    // Método que foi corrigido para aceitar apenas um tipo 'String' para o parâmetro 'email'.
    public void setEmail(String email) {
        this.email = email;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


    /**
     * Implementa a regra de negócio para validação do CPF (Módulo 11).
     *
     * @return boolean Retorna 'true' se o CPF for matematicamente válido, 'false' caso contrário.
     */
    public boolean isCpfValido() {
        if (cpf == null) return false;
        // Normaliza o CPF, removendo caracteres não numéricos.
        String num = cpf.replaceAll("\\D", "");

        // Verifica o tamanho e se todos os dígitos são iguais (o que invalida o CPF).
        if (num.length() != 11) return false;
        if (num.chars().distinct().count() == 1) return false;

        try {
            // Processo de cálculo para o primeiro dígito verificador.
            int sum = 0;
            for (int i = 0; i < 9; i++) sum += (num.charAt(i) - '0') * (10 - i);
            int d1 = 11 - (sum % 11);
            if (d1 >= 10) d1 = 0;

            // Processo de cálculo para o segundo dígito verificador.
            sum = 0;
            for (int i = 0; i < 10; i++) sum += (num.charAt(i) - '0') * (11 - i);
            int d2 = 11 - (sum % 11);
            if (d2 >= 10) d2 = 0;

            // Retorna o resultado da comparação dos dígitos calculados com os dígitos originais.
            return (num.charAt(9) - '0' == d1) && (num.charAt(10) - '0' == d2);
        } catch (Exception e) {
            // Captura qualquer erro genérico que possa ocorrer durante a conversão.
            return false;
        }
    }
}