package br.com.creedev.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.creedev.domain.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

	Page<Estado> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
}