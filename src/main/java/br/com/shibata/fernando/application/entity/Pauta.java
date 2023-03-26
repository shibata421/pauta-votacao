package br.com.shibata.fernando.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pautas")
public class Pauta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String descricao;

    @Column
    private LocalDateTime horarioFim;

    @OneToMany(mappedBy = "pauta")
    private Set<Voto> votos;

    @Transient
    private long somaSim;

    @Transient
    private long somaNao;

    @JsonIgnore
    public Set<Voto> getVotos() {
        return votos;
    }
}
