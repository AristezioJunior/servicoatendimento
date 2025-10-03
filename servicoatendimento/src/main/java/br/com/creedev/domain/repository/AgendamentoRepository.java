package br.com.creedev.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.creedev.domain.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END
        FROM Agendamento a
        WHERE a.profissional.id = :profissionalId
        AND a.statusAgendamento NOT IN ('CANCELADO', 'CONCLUIDO')
        AND (COALESCE(:agendamentoToExcludeId, 0) = 0 OR a.id <> :agendamentoToExcludeId)
        AND (:inicio < a.dataHoraFim AND :fim > a.dataHoraInicio)
        """)
    boolean hasConflict(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim,
        @Param("profissionalId") Long profissionalId,
        @Param("agendamentoToExcludeId") Long agendamentoToExcludeId
    );

    @Query("""
        SELECT a FROM Agendamento a
        WHERE (COALESCE(:profissionalId, 0) = 0 OR a.profissional.id = :profissionalId)
        AND (COALESCE(:petId, 0) = 0 OR a.pet.id = :petId)
        AND (CAST(:dataInicio AS timestamp) IS NULL OR a.dataHoraInicio >= :dataInicio)
        AND (CAST(:dataFim AS timestamp) IS NULL OR a.dataHoraFim <= :dataFim)
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