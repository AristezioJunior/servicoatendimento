package br.com.creedev.api.dto.request;

import java.time.LocalDateTime;

import br.com.creedev.domain.model.Enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {
    @NotNull
    private Long petId;
    @NotNull
    private Long profissionalId;
    @NotNull
    private Long servicoId;
    @NotNull
    private LocalDateTime dataHoraInicio;
    @NotNull
    private LocalDateTime dataHoraFim;
    
    private StatusAgendamento statusAgendamento;
    
    private String observacao;
}