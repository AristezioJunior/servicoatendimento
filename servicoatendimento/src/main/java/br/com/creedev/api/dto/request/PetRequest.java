package br.com.creedev.api.dto.request;

import java.time.LocalDate;

import br.com.creedev.domain.model.Enums.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {
    @NotBlank
    private String nome;
    @NotNull
    private Long especieId;
    private Long racaId;
    @NotNull
    private Sexo sexo;
    @NotNull
    private Double peso;
    private LocalDate dataNascimento;
    private String observacoes;
    @NotNull
    private Long clienteId;
}