package br.com.shibata.fernando.application.repository;

import br.com.shibata.fernando.application.entity.Associado;
import br.com.shibata.fernando.application.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociadoRepository extends JpaRepository<Associado, Integer> {
}
