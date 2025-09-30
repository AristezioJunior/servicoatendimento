package br.com.creedev.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.creedev.domain.model.Enums.StatusEntidade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalhes do serviço oferecido pela clínica.")
public class ServicoResponse {

    @Schema(description = "Identificador único do serviço.", example = "15")
    private Long id;

    @Schema(description = "Nome do serviço (ex: Banho e Tosa).", example = "Consulta Veterinária")
    private String nome;

    @Schema(description = "Descrição detalhada do serviço.", example = "Consulta de rotina com avaliação completa de saúde.")
    private String descricao;

    @Schema(description = "Preço base do serviço.", example = "120.50")
    private BigDecimal precoBase;

    @Schema(description = "Duração estimada do serviço em minutos.", example = "30")
    private Integer duracaoMinutos;

    @Schema(description = "Status lógico da entidade (ATIVO/INATIVO).", example = "ATIVO")
    private StatusEntidade status;

    @Schema(description = "Data e hora de criação do registro.", example = "2023-11-01T08:00:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última alteração do registro.", example = "2024-01-10T11:20:00")
    private LocalDateTime dataAlteracao;
}