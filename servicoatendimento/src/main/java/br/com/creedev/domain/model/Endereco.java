package br.com.creedev.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

    @NotBlank
    @Size(max = 9)
    @Column(name = "endereco_cep", length = 9, nullable = false)
    private String cep;

    @NotBlank
    @Size(max = 150)
    @Column(name = "endereco_logradouro", length = 150, nullable = false)
    private String logradouro;

    @NotBlank
    @Size(max = 10)
    @Column(name = "endereco_numero", length = 10, nullable = false)
    private String numero;

    @Size(max = 50)
    @Column(name = "endereco_complemento", length = 50)
    private String complemento;

    @NotBlank
    @Size(max = 100)
    @Column(name = "endereco_bairro", length = 100, nullable = false)
    private String bairro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id", nullable = false)
    private Cidade cidade;
}