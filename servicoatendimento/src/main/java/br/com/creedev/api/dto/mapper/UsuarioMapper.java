package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.UsuarioRequest;
import br.com.creedev.domain.model.Usuario;

@Component
public class UsuarioMapper {
    
    public Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
            .email(request.email())
            .senha(request.senha())
            .nome(request.nome())
            .build();
    }
}