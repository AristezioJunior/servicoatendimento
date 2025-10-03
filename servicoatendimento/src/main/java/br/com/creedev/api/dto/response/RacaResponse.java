package br.com.creedev.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalhes da raça de um Pet.")
public class RacaResponse {

    @Schema(description = "Identificador único da raça.", example = "3")
    private Long id;

    @Schema(description = "Nome da raça.", example = "Labrador")
    private String nome;

    @Schema(description = "Informações da espécie à qual a raça pertence.")
    private EspecieResponse especie;
}