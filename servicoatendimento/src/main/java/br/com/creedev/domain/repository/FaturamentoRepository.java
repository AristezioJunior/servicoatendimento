package br.com.creedev.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.creedev.domain.model.Faturamento;

@Repository
public interface FaturamentoRepository extends JpaRepository<Faturamento, Long> {

}