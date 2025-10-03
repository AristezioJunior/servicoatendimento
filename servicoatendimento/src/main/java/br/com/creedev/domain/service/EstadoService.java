package br.com.creedev.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.EstadoMapper;
import br.com.creedev.api.dto.request.EstadoRequest;
import br.com.creedev.api.dto.response.EstadoResponse;
import br.com.creedev.domain.model.Estado;
import br.com.creedev.domain.repository.EstadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    @Transactional
    public EstadoResponse salvar(EstadoRequest request) {
        // Mapeia e Salva
        Estado estado = estadoMapper.toEntity(request);
        Estado salvo = estadoRepository.save(estado);
        
        return estadoMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public EstadoResponse buscarPorId(Long id) {
        Estado estado = buscarEntidade(id);
        return estadoMapper.toResponse(estado);
    }

    @Transactional(readOnly = true)
    public Page<EstadoResponse> listarTodos(Pageable pageable) {
        return estadoRepository.findAll(pageable)
                .map(estadoMapper::toResponse);
    }
    
    /**
     * Busca Estados por parte do nome, ignorando case, com suporte a paginação.
     * Requer o método findByNomeContainingIgnoreCase no EstadoRepository.
     */
    @Transactional(readOnly = true)
    public Page<EstadoResponse> buscarPorNome(String termo, Pageable pageable) {
        // Assumindo a existência do método de busca no Repository
        return estadoRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(estadoMapper::toResponse);
    }

    @Transactional
    public EstadoResponse atualizar(Long id, EstadoRequest request) {
        Estado existente = buscarEntidade(id);

        // Atualiza entidade via mapper
        estadoMapper.updateEntityFromRequest(request, existente);

        Estado salvo = estadoRepository.save(existente);
        return estadoMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Estado existente = buscarEntidade(id);
        estadoRepository.delete(existente);
    }

    // --- Métodos de Ajuda ---

    private Estado buscarEntidade(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado: " + id));
    }
}