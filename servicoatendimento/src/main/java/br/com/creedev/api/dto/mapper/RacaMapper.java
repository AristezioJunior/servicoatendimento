package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.RacaRequest;
import br.com.creedev.api.dto.response.RacaResponse;
import br.com.creedev.domain.model.Especie;
import br.com.creedev.domain.model.Raca;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RacaMapper {

    // Dependência injetada
    private final EspecieMapper especieMapper;

    public RacaResponse toResponse(Raca raca) {
        if (raca == null) return null;
        return RacaResponse.builder()
                .id(raca.getId())
                .nome(raca.getNome())
                // Chama o método da instância injetada
                .especie(especieMapper.toResponse(raca.getEspecie()))
                .build();
    }

    public Raca toEntity(RacaRequest request, Especie especie) {
        if (request == null) return null;
        Raca raca = new Raca();
        raca.setNome(request.getNome());
        // A entidade Especie já foi resolvida no Service
        raca.setEspecie(especie);
        return raca;
    }
    
    /**
     * Atualiza a entidade Raca existente com os dados do request e entidade Especie resolvida.
     */
    public void updateEntityFromRequest(RacaRequest request, Raca existente, Especie especie) {
        if (request == null || existente == null) return;
        
        // Atualiza o nome se ele for fornecido
        if (request.getNome() != null) {
            existente.setNome(request.getNome());
        }
        
        // Atualiza a espécie se o Service resolveu uma nova espécie
        if (especie != null) {
            existente.setEspecie(especie);
        }
    }
}