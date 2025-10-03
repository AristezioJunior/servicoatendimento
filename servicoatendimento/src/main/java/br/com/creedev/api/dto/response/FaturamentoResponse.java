package br.com.creedev.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.creedev.domain.model.Enums.FormaPagamento;
import br.com.creedev.domain.model.Enums.StatusEntidade;
import br.com.creedev.domain.model.Enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaturamentoResponse {
    private Long id;
    private AgendamentoResponse agendamento;
    private LocalDate dataReferencia;
    private BigDecimal valorServico;
    private BigDecimal desconto;
    private BigDecimal valorFinal;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private StatusEntidade status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;
}