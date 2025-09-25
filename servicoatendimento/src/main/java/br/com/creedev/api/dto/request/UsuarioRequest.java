package br.com.creedev.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
	@Email String email,
	@NotBlank String senha,
	@NotBlank String nome
) {}