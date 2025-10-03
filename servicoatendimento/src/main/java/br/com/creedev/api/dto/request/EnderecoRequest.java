package br.com.creedev.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {

        @NotBlank
        @Size(max = 9)
        String cep;

        @NotBlank
        @Size(max = 150)
        String logradouro;

        @NotBlank
        @Size(max = 10)
        String numero;

        @Size(max = 50)
        String complemento;

        @NotBlank
        @Size(max = 100)
        String bairro;

        @NotBlank
        Long cidadeId;
}