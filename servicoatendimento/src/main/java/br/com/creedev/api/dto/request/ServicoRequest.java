package br.com.creedev.api.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoRequest {
    @NotBlank
    private String nome;
    private String descricao;
    @NotNull
    @PositiveOrZero
    private BigDecimal precoBase;
    @NotNull
    @Min(5)
    private Integer duracaoMinutos;
}