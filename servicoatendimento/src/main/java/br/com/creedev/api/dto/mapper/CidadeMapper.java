package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.CidadeRequest;
import br.com.creedev.api.dto.response.CidadeResponse;
import br.com.creedev.domain.model.Cidade;
import br.com.creedev.domain.model.Estado;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CidadeMapper {

    // Dependência injetada (precisa ser uma instância, não estática)
    private final EstadoMapper estadoMapper;

    public CidadeResponse toResponse(Cidade cidade) {
        if (cidade == null) return null;
        return CidadeResponse.builder()
                .id(cidade.getId())
                .nome(cidade.getNome())
                // Chama o método da instância injetada
                .estado(estadoMapper.toResponse(cidade.getEstado()))
                .build();
    }

    public Cidade toEntity(CidadeRequest request, Estado estado) {
        if (request == null) return null;
        Cidade cidade = new Cidade();
        cidade.setNome(request.getNome());
        // O Service já resolveu a entidade Estado
        cidade.setEstado(estado);
        return cidade;
    }
    
    /**
     * Atualiza a entidade existente com os dados do request.
     */
    public void updateEntityFromRequest(CidadeRequest request, Cidade existente, Estado estado) {
        if (request == null || existente == null) return;
        
        // Se o nome vier, atualiza
        if (request.getNome() != null && !request.getNome().equals(existente.getNome())) {
            existente.setNome(request.getNome());
        }
        
        // Se a entidade Estado foi resolvida (houve mudança ou veio no request), atualiza
        if (estado != null) {
            existente.setEstado(estado);
        }
    }
}