package com.example.microservico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropostaResponseDTO {
    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String telefone;

    private Double renda;

    private Double valorSolicitado;

    private int prazoPagamento;

    private Boolean aprovada;

    private String observacao;

}
