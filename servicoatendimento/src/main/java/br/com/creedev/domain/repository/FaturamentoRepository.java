package br.com.creedev.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.creedev.domain.model.Faturamento;

public interface FaturamentoRepository extends JpaRepository<Faturamento, Long> {

    boolean existsByAgendamento_Id(Long agendamentoId);

    Optional<Faturamento> findByAgendamento_Id(Long agendamentoId);

    @Query("SELECT f FROM Faturamento f WHERE f.dataReferencia BETWEEN :inicio AND :fim")
    List<Faturamento> buscarPorPeriodo(LocalDate inicio, LocalDate fim);

    @Query("SELECT COALESCE(SUM(f.valorFinal), 0) FROM Faturamento f WHERE f.dataReferencia BETWEEN :inicio AND :fim")
    BigDecimal somarPorPeriodo(LocalDate inicio, LocalDate fim);

    @Query("""
        SELECT f.formaPagamento, COALESCE(SUM(f.valorFinal), 0)
        FROM Faturamento f
        WHERE f.dataReferencia BETWEEN :inicio AND :fim
        GROUP BY f.formaPagamento
    """)
    List<Object[]> somarPorFormaPagamento(LocalDate inicio, LocalDate fim);
}