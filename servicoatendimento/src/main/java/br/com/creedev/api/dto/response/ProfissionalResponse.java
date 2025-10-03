package br.com.creedev.api.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import br.com.creedev.domain.model.Enums.ProfissionalEspecialidade;
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
@Schema(description = "Resposta completa do profissional.")
public class ProfissionalResponse {

    @Schema(description = "Identificador único do profissional.", example = "2")
    private Long id;

    @Schema(description = "Nome completo do profissional.", example = "Dr. Bruno Vet")
    private String nome;

    @Schema(description = "Especialidade do profissional.", example = "VETERINARIO")
    private ProfissionalEspecialidade especialidade;

    @Schema(description = "Número de telefone fixo.", example = "5133335555")
    private String telefoneFixo;

    @Schema(description = "Número de celular.", example = "51999998888")
    private String celular;

    @Schema(description = "Endereço de e-mail do profissional.", example = "bruno.vet@clinicavet.com")
    private String email;

    @Schema(description = "Detalhes do endereço do profissional.")
    private EnderecoResponse endereco;

    @Schema(description = "Lista de serviços que este profissional pode realizar.")
    private Set<ServicoResponse> servicos;

    @Schema(description = "Observações gerais sobre o profissional.")
    private String observacoes;

    @Schema(description = "Status lógico da entidade (ATIVO/INATIVO).", example = "ATIVO")
    private StatusEntidade status;

    @Schema(description = "Data e hora de criação do registro.", example = "2023-01-01T10:00:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última alteração do registro.", example = "2024-07-25T14:00:00")
    private LocalDateTime dataAlteracao;
}