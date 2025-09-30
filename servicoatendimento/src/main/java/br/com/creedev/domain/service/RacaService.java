package br.com.creedev.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.RacaMapper;
import br.com.creedev.api.dto.request.RacaRequest;
import br.com.creedev.api.dto.response.RacaResponse;
import br.com.creedev.domain.model.Especie;
import br.com.creedev.domain.model.Raca;
import br.com.creedev.domain.repository.EspecieRepository;
import br.com.creedev.domain.repository.RacaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RacaService {

    private final RacaRepository racaRepository;
    private final RacaMapper racaMapper;
    private final EspecieRepository especieRepository; // Para resolver a dependência

    @Transactional
    public RacaResponse salvar(RacaRequest request) {
        // 1. Resolve Especie: Garante que a espécie referenciada exista
        Especie especie = buscarEspecie(request.getEspecieId());
        
        // 2. Mapeia e Salva
        Raca raca = racaMapper.toEntity(request, especie);
        Raca salvo = racaRepository.save(raca);
        
        return racaMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public RacaResponse buscarPorId(Long id) {
        Raca raca = buscarEntidade(id);
        return racaMapper.toResponse(raca);
    }

    @Transactional(readOnly = true)
    public Page<RacaResponse> listarTodos(Pageable pageable) {
        return racaRepository.findAll(pageable)
                .map(racaMapper::toResponse);
    }
    
    /**
     * Busca Raças por parte do nome (case insensitive).
     * Requer o método findByNomeContainingIgnoreCase no RacaRepository.
     */
    @Transactional(readOnly = true)
    public Page<RacaResponse> buscarPorNome(String termo, Pageable pageable) {
        // Assumindo a existência do método de busca no Repository
        return racaRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(racaMapper::toResponse);
    }

    @Transactional
    public RacaResponse atualizar(Long id, RacaRequest request) {
        Raca existente = buscarEntidade(id);

        // 1. Resolve Especie: Garante que a espécie referenciada para atualização exista
        Especie novaEspecie = buscarEspecie(request.getEspecieId());

        // 2. Atualiza entidade via mapper
        racaMapper.updateEntityFromRequest(request, existente, novaEspecie);

        Raca salvo = racaRepository.save(existente);
        return racaMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Raca existente = buscarEntidade(id);
        racaRepository.delete(existente);
    }

    // --- Métodos de Ajuda ---

    private Raca buscarEntidade(Long id) {
        return racaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada: " + id));
    }
    
    private Especie buscarEspecie(Long especieId) {
        return especieRepository.findById(especieId)
                .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada: " + especieId));
    }
}