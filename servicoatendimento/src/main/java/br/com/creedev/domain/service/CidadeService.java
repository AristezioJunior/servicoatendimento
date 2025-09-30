package br.com.creedev.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.CidadeMapper;
import br.com.creedev.api.dto.request.CidadeRequest;
import br.com.creedev.api.dto.response.CidadeResponse;
import br.com.creedev.domain.model.Cidade;
import br.com.creedev.domain.model.Estado;
import br.com.creedev.domain.repository.CidadeRepository;
import br.com.creedev.domain.repository.EstadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;
    private final EstadoRepository estadoRepository; // Para resolver a dependência

    @Transactional
    public CidadeResponse salvar(CidadeRequest request) {
        // 1. Resolve Estado: Garante que o estado referenciado exista
        Estado estado = buscarEstado(request.getEstadoId());

        // 2. Mapeia e Salva
        Cidade cidade = cidadeMapper.toEntity(request, estado);
        Cidade salvo = cidadeRepository.save(cidade);
        
        return cidadeMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public CidadeResponse buscarPorId(Long id) {
        Cidade cidade = buscarEntidade(id);
        return cidadeMapper.toResponse(cidade);
    }

    @Transactional(readOnly = true)
    public Page<CidadeResponse> listarTodos(Pageable pageable) {
        return cidadeRepository.findAll(pageable)
                .map(cidadeMapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<CidadeResponse> buscarPorNome(String termo, Pageable pageable) {
        // Assume a existência de findByNomeContainingIgnoreCase no CidadeRepository
        return cidadeRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(cidadeMapper::toResponse);
    }

    @Transactional
    public CidadeResponse atualizar(Long id, CidadeRequest request) {
        Cidade existente = buscarEntidade(id);

        // 1. Resolve Estado: Pode ter mudado o estado (o que é raro, mas possível)
        Estado novoEstado = buscarEstado(request.getEstadoId());

        // 2. Atualiza entidade via mapper
        cidadeMapper.updateEntityFromRequest(request, existente, novoEstado);

        Cidade salvo = cidadeRepository.save(existente);
        return cidadeMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Cidade existente = buscarEntidade(id);
        cidadeRepository.delete(existente);
    }

    // --- Métodos de Ajuda ---

    private Cidade buscarEntidade(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + id));
    }

    private Estado buscarEstado(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado: " + estadoId));
    }
}