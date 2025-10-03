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
@Schema(description = "Detalhes da cidade, incluindo o estado relacionado.")
public class CidadeResponse {

    @Schema(description = "Identificador único da cidade.", example = "5")
    private Long id;

    @Schema(description = "Nome da cidade.", example = "Porto Alegre")
    private String nome;

    @Schema(description = "Informações do estado ao qual a cidade pertence.")
    private EstadoResponse estado;
}