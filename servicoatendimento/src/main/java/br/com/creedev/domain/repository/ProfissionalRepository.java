package br.com.creedev.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.creedev.domain.model.Profissional;
import br.com.creedev.domain.model.Enums.StatusEntidade;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

	Page<Profissional> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
    // Método para verificar se um profissional existe pelo ID E se o seu status é ATIVO.
    // 'Status' deve ser o nome do campo na entidade Profissional.
    // O retorno 'boolean' é perfeito.
    boolean existsByIdAndStatus(Long id, StatusEntidade status); // Altere 'StatusProfissional' para o tipo que você usa.
}