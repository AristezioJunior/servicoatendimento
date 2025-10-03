package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.EstadoRequest;
import br.com.creedev.api.dto.response.EstadoResponse;
import br.com.creedev.domain.model.Estado;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoMapper {

    public EstadoResponse toResponse(Estado estado) {
        if (estado == null) return null;
        return EstadoResponse.builder()
                .id(estado.getId())
                .nome(estado.getNome())
                // Adicione outros campos de auditoria/status se necessário
                // .status(estado.getStatus()) 
                // .dataCriacao(estado.getDataCriacao())
                .build();
    }

    public Estado toEntity(EstadoRequest request) {
        if (request == null) return null;
        Estado estado = new Estado();
        estado.setNome(request.getNome());
        return estado;
    }
    
    /**
     * Atualiza a entidade existente com os dados do request.
     */
    public void updateEntityFromRequest(EstadoRequest request, Estado existente) {
        if (request == null || existente == null) return;
        
        if (request.getNome() != null && !request.getNome().equals(existente.getNome())) {
            existente.setNome(request.getNome());
        }
    }
}