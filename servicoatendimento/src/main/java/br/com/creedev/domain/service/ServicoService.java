package br.com.creedev.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.ServicoMapper;
import br.com.creedev.api.dto.request.ServicoRequest;
import br.com.creedev.api.dto.response.ServicoResponse;
import br.com.creedev.domain.model.Servico;
import br.com.creedev.domain.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;

    @Transactional
    public ServicoResponse salvar(ServicoRequest request) {
        Servico servico = servicoMapper.toEntity(request);
        Servico salvo = servicoRepository.save(servico);
        
        return servicoMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public ServicoResponse buscarPorId(Long id) {
        Servico servico = buscarEntidade(id);
        return servicoMapper.toResponse(servico);
    }

    @Transactional(readOnly = true)
    public Page<ServicoResponse> listarTodos(Pageable pageable) {
        return servicoRepository.findAll(pageable)
                .map(servicoMapper::toResponse);
    }
    
    /**
     * Busca Serviços por parte do nome ou descrição (case insensitive).
     * Requer o método findByNomeContainingIgnoreCase no ServicoRepository.
     */
    @Transactional(readOnly = true)
    public Page<ServicoResponse> buscarPorNome(String termo, Pageable pageable) {
        // Assumindo findByNomeContainingIgnoreCase como padrão de busca no Repositório
        return servicoRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(servicoMapper::toResponse);
    }

    @Transactional
    public ServicoResponse atualizar(Long id, ServicoRequest request) {
        Servico existente = buscarEntidade(id);

        servicoMapper.updateEntityFromRequest(request, existente);

        Servico salvo = servicoRepository.save(existente);
        return servicoMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Servico existente = buscarEntidade(id);
        // Regra: Pode ser necessário verificar se há agendamentos pendentes antes de deletar
        servicoRepository.delete(existente);
    }

    // --- Métodos de Ajuda ---

    public Servico buscarEntidade(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado: " + id));
    }
}