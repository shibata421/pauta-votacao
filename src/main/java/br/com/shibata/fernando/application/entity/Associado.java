package br.com.shibata.fernando.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "associados")
@NoArgsConstructor
public class Associado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String nome;

    @Column
    private String cpf;

    @OneToMany(mappedBy = "associado")
    @JsonIgnore
    private Set<Voto> votos;
}
