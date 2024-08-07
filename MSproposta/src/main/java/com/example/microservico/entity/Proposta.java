package com.example.microservico.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private Boolean aprovada;

    private  boolean integrada;

    private String observacao;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario")
    //É usada na biblioteca Jackson para resolver problemas de referência circular durante a serialização e desserialização de objetos Java para JSON. Ela funciona em conjunto com a anotação @JsonBackReference.
    @JsonManagedReference
    private Usuario usuario;

}
