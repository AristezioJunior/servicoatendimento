package br.com.creedev.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.creedev.domain.model.Servico;
import br.com.creedev.domain.model.Enums.StatusEntidade;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    // Busca paginada por nome (case insensitive)
    Page<Servico> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    // Busca todos os serviços com determinado status
    List<Servico> findByStatus(StatusEntidade status);

    // Caso precise filtrar por nome + status
    Page<Servico> findByNomeContainingIgnoreCaseAndStatus(String nome, StatusEntidade status, Pageable pageable);
}