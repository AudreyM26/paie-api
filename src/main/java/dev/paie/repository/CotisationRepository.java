package dev.paie.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.paie.entite.Cotisation;

public interface CotisationRepository extends JpaRepository<Cotisation, Integer>{

	//BigDecimal findByImposable(Boolean imposable);
	@Query("select sum(tauxSalarial) from Cotisation c where imposable = :imposable and id in :listeId")
	BigDecimal sumTauxSalarial(@Param("imposable") Boolean imposable,@Param("listeId") List<Integer> listeId);

}
