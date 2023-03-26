package br.com.shibata.fernando.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "votos")
@NoArgsConstructor
@Getter
@Setter
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "associado_id")
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @Column
    private String voto;
}
