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
@Schema(description = "Detalhes do endereço.")
public class EnderecoResponse {

    @Schema(description = "Código de Endereçamento Postal.", example = "90000000")
    String cep;

    @Schema(description = "Nome da rua, avenida ou praça.", example = "Rua Teste de Logradouro")
    String logradouro;

    @Schema(description = "Número do imóvel.", example = "1230")
    String numero;

    @Schema(description = "Complemento do endereço (apartamento, bloco, etc.).", example = "Apto 402")
    String complemento;

    @Schema(description = "Nome do bairro.", example = "Centro Histórico")
    String bairro;

    @Schema(description = "Informações da cidade relacionada.")
    CidadeResponse cidade;
}