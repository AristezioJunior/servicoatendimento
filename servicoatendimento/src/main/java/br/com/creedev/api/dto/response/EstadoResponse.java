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
@Schema(description = "Detalhes do estado.")
public class EstadoResponse {

    @Schema(description = "Identificador único do estado.", example = "23")
    private Long id;

    @Schema(description = "Nome do estado (ex: Rio Grande do Sul).", example = "Rio Grande do Sul")
    private String nome;
}