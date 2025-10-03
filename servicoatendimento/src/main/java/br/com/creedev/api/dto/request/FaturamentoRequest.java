package br.com.creedev.api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.creedev.domain.model.Enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaturamentoRequest {
    @NotNull
    private Long agendamentoId;
    @NotNull
    private LocalDate dataReferencia;
    @NotNull
    private BigDecimal valorServico;
    private BigDecimal desconto;
    @NotNull
    private BigDecimal valorFinal;
    @NotNull
    private FormaPagamento formaPagamento;
}