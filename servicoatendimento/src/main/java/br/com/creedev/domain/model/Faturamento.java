package br.com.creedev.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.creedev.domain.model.Enums.FormaPagamento;
import br.com.creedev.domain.model.Enums.StatusPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "faturamentos")
public class Faturamento extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agendamento_id", nullable = false, unique = true)
    private Agendamento agendamento;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate dataReferencia;

    @NotNull
    @Column(name = "valor_servico", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorServico;

    @Column(name = "desconto", precision = 10, scale = 2)
    private BigDecimal desconto;

    @NotNull
    @Column(name = "valor_final", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", length = 20, nullable = false)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", length = 20, nullable = false)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;
}