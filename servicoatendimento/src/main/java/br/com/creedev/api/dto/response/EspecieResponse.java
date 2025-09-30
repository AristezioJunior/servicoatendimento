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
@Schema(description = "Detalhes da espécie de um Pet (ex: Cão, Gato).")
public class EspecieResponse {

    @Schema(description = "Identificador único da espécie.", example = "1")
    private Long id;

    @Schema(description = "Nome da espécie.", example = "Cão")
    private String nome;
}