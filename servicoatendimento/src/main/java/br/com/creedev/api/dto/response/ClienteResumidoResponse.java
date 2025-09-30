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
@Schema(description = "Resposta resumida do cliente, utilizada como referência em outras entidades (ex: Pet).")
public class ClienteResumidoResponse {

    @Schema(description = "Identificador único do cliente.", example = "1")
    private Long id;

    @Schema(description = "Nome completo do cliente/dono.", example = "João da Silva")
    private String nome;
}