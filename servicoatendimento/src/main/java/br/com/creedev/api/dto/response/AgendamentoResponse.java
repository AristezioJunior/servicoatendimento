package br.com.creedev.api.dto.response;

import java.time.LocalDateTime;

import br.com.creedev.domain.model.Enums.StatusAgendamento;
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
@Schema(description = "Detalhes do agendamento realizado, incluindo informações do Pet, Profissional e Serviço.")
public class AgendamentoResponse {

    @Schema(description = "Identificador único do agendamento.", example = "10")
    private Long id;

    @Schema(description = "Informações resumidas do Pet agendado.")
    private PetResponse pet;

    @Schema(description = "Informações resumidas do Profissional responsável pelo agendamento.")
    private ProfissionalResponse profissional;

    @Schema(description = "Detalhes do Serviço agendado.")
    private ServicoResponse servico;

    @Schema(description = "Data e hora de início do agendamento.", example = "2025-10-20T10:00:00")
    private LocalDateTime dataHoraInicio;

    @Schema(description = "Data e hora de fim prevista para o agendamento.", example = "2025-10-20T10:30:00")
    private LocalDateTime dataHoraFim;

    @Schema(description = "Status atual do agendamento (PENDENTE, CONCLUIDO, CANCELADO).", example = "PENDENTE")
    private StatusAgendamento statusAgendamento;

    @Schema(description = "Observações adicionais sobre o agendamento ou o serviço.")
    private String observacao;

    @Schema(description = "Status lógico da entidade (ATIVO/INATIVO).", example = "ATIVO")
    private StatusEntidade status;

    @Schema(description = "Data e hora de criação do registro.", example = "2025-10-18T15:30:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última alteração do registro.", example = "2025-10-19T09:15:00")
    private LocalDateTime dataAlteracao;
}