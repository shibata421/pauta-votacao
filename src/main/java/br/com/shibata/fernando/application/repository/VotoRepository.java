package br.com.shibata.fernando.application.repository;

import br.com.shibata.fernando.application.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Integer> {

    Optional<Voto> findByAssociadoIdAndPautaId(Integer associadoId, Integer pautaId);

}
