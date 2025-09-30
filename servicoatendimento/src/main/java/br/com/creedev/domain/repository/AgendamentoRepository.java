package br.com.creedev.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.creedev.domain.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END
            FROM Agendamento a
            WHERE a.profissional.id = :profissionalId
              AND a.statusAgendamento NOT IN ('CANCELADO', 'CONCLUIDO')
              AND (:agendamentoToExcludeId IS NULL OR a.id <> :agendamentoToExcludeId)
              AND (
                (:inicio < a.dataHoraFim AND :fim > a.dataHoraInicio)
              )
            """)
        boolean hasConflict(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("profissionalId") Long profissionalId,
            @Param("agendamentoToExcludeId") Long agendamentoToExcludeId
        );
    
    @Query("""
            SELECT a FROM Agendamento a
            WHERE (:profissionalId IS NULL OR a.profissional.id = :profissionalId)
              AND (:petId IS NULL OR a.pet.id = :petId)
              AND (:dataInicio IS NULL OR a.dataHoraInicio >= :dataInicio)
              AND (:dataFim IS NULL OR a.dataHoraFim <= :dataFim)
            ORDER BY a.dataHoraInicio ASC
            """)
        Page<Agendamento> buscarPorFiltros(
            @Param("profissionalId") Long profissionalId,
            @Param("petId") Long petId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
        );

}