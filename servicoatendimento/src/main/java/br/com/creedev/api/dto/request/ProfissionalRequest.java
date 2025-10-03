package br.com.creedev.api.dto.request;

import java.util.Set;

import br.com.creedev.domain.model.Enums.ProfissionalEspecialidade;
import jakarta.validation.constraints.Email;
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
public class ProfissionalRequest {
    @NotBlank
    private String nome;
    @NotNull
    private ProfissionalEspecialidade especialidade;
    private String telefoneFixo;
    @NotBlank
    private String celular;
    @Email
    private String email;
    @NotNull
    private EnderecoRequest endereco;
    private Set<Long> servicoIds;
    private String observacoes;
}