package br.com.creedev.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.creedev.domain.model.Raca;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long>{

	Page<Raca> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	List<Raca> findByEspecieId(Long especieId);
}