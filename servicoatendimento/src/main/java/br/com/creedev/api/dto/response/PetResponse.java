package br.com.creedev.api.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.creedev.domain.model.Enums.Sexo;
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
@Schema(description = "Resposta completa do Pet.")
public class PetResponse {

    @Schema(description = "Identificador único do Pet.", example = "7")
    private Long id;

    @Schema(description = "Nome do Pet.", example = "Max")
    private String nome;

    @Schema(description = "Informações da espécie do Pet.")
    private EspecieResponse especie;

    @Schema(description = "Informações da raça do Pet.")
    private RacaResponse raca;

    @Schema(description = "Gênero do Pet.", example = "MASCULINO")
    private Sexo sexo;

    @Schema(description = "Peso do Pet em quilogramas.", example = "12.5")
    private Double peso;

    @Schema(description = "Data de nascimento do Pet.", example = "2020-01-15")
    private LocalDate dataNascimento;

    @Schema(description = "Observações gerais sobre o Pet.")
    private String observacoes;

    @Schema(description = "Informações resumidas do dono (Cliente) do Pet.")
    private ClienteResumidoResponse dono;

    @Schema(description = "Status lógico da entidade (ATIVO/INATIVO).", example = "ATIVO")
    private StatusEntidade status;

    @Schema(description = "Data e hora de criação do registro.", example = "2024-06-20T11:00:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última alteração do registro.", example = "2024-06-21T14:30:00")
    private LocalDateTime dataAlteracao;
}