package br.com.creedev.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.EspecieMapper;
import br.com.creedev.api.dto.request.EspecieRequest;
import br.com.creedev.api.dto.response.EspecieResponse;
import br.com.creedev.domain.model.Especie;
import br.com.creedev.domain.repository.EspecieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecieService {

    private final EspecieRepository especieRepository;
    private final EspecieMapper especieMapper;

    @Transactional
    public EspecieResponse salvar(EspecieRequest request) {
        Especie especie = especieMapper.toEntity(request);
        Especie salvo = especieRepository.save(especie);
        
        return especieMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public EspecieResponse buscarPorId(Long id) {
        Especie especie = buscarEntidade(id);
        return especieMapper.toResponse(especie);
    }

    @Transactional(readOnly = true)
    public Page<EspecieResponse> listarTodos(Pageable pageable) {
        return especieRepository.findAll(pageable)
                .map(especieMapper::toResponse);
    }
    
    /**
     * Busca Espécies por parte do nome (case insensitive).
     * Requer o método findByNomeContainingIgnoreCase no EspecieRepository.
     */
    @Transactional(readOnly = true)
    public Page<EspecieResponse> buscarPorNome(String termo, Pageable pageable) {
        return especieRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(especieMapper::toResponse);
    }

    @Transactional
    public EspecieResponse atualizar(Long id, EspecieRequest request) {
        Especie existente = buscarEntidade(id);

        especieMapper.updateEntityFromRequest(request, existente);

        Especie salvo = especieRepository.save(existente);
        return especieMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Especie existente = buscarEntidade(id);
        especieRepository.delete(existente);
    }

    // --- Métodos de Ajuda ---

    private Especie buscarEntidade(Long id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada: " + id));
    }
}