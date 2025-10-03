package br.com.creedev.api.dto.response;

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
@Schema(description = "Resposta completa do cliente.")
public class ClienteResponse {

    @Schema(description = "Identificador único do cliente.", example = "1")
    private Long id;

    @Schema(description = "Nome completo do cliente/dono.", example = "João da Silva")
    private String nome;

    @Schema(description = "CPF do cliente (apenas números).", example = "12345678900")
    private String cpf;

    @Schema(description = "Número de telefone fixo.", example = "5133334444")
    private String telefoneFixo;

    @Schema(description = "Número de celular.", example = "51988887777")
    private String celular;

    @Schema(description = "Endereço de e-mail do cliente.", example = "joao.silva@exemplo.com")
    private String email;

    @Schema(description = "Detalhes do endereço do cliente.")
    private EnderecoResponse endereco;

    @Schema(description = "Status lógico da entidade (ATIVO/INATIVO).", example = "ATIVO")
    private StatusEntidade status;

    @Schema(description = "Data e hora de criação do registro.", example = "2024-05-10T09:00:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última alteração do registro.", example = "2024-05-10T10:15:00")
    private LocalDateTime dataAlteracao;
}