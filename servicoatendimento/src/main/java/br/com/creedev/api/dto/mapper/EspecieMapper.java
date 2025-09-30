package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.EspecieRequest;
import br.com.creedev.api.dto.response.EspecieResponse;
import br.com.creedev.domain.model.Especie;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EspecieMapper {

    public EspecieResponse toResponse(Especie especie) {
        if (especie == null) return null;
        return EspecieResponse.builder()
                .id(especie.getId())
                .nome(especie.getNome())
                .build();
    }

    public Especie toEntity(EspecieRequest request) {
        if (request == null) return null;
        Especie especie = new Especie();
        especie.setNome(request.getNome());
        return especie;
    }
    
    /**
     * Atualiza a entidade Especie existente com os dados do request.
     */
    public void updateEntityFromRequest(EspecieRequest request, Especie existente) {
        if (request == null || existente == null) return;
        
        // Apenas atualiza o nome se ele for fornecido no request
        if (request.getNome() != null) {
            existente.setNome(request.getNome());
        }
    }
}