package br.com.creedev.domain.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.AgendamentoMapper;
import br.com.creedev.api.dto.request.AgendamentoRequest;
import br.com.creedev.api.dto.response.AgendamentoResponse;
import br.com.creedev.domain.model.Agendamento;
import br.com.creedev.domain.model.Pet;
import br.com.creedev.domain.model.Profissional;
import br.com.creedev.domain.model.Servico;
import br.com.creedev.domain.repository.AgendamentoRepository;
import br.com.creedev.domain.repository.PetRepository;
import br.com.creedev.domain.repository.ProfissionalRepository;
import br.com.creedev.domain.repository.ServicoRepository;
import br.com.creedev.domain.validator.AgendamentoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;
    // Repositórios para resolver entidades relacionadas
    private final PetRepository petRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;
    // private final AgendamentoValidator agendamentoValidator; // Injeção opcional para regras complexas

    private final AgendamentoValidator agendamentoValidator; 
    
    /**
     * Cria um novo agendamento.
     */
    @Transactional
    public AgendamentoResponse salvar(AgendamentoRequest request) {
        // 1. Resolver Entidades
        Pet pet = buscarPet(request.getPetId());
        Profissional profissional = buscarProfissional(request.getProfissionalId());
        Servico servico = buscarServico(request.getServicoId());

        // 2. Validações de Negócio: DELEGAÇÃO TOTAL
        agendamentoValidator.validarAgendamento(
            request.getDataHoraInicio(), 
            request.getDataHoraFim(), 
            profissional.getId(), 
            null // ID nulo indica CRIAÇÃO
        );

        // 3. Mapear e Salvar
        Agendamento agendamento = agendamentoMapper.toEntity(request, pet, profissional, servico);
        Agendamento salvo = agendamentoRepository.save(agendamento);

        return agendamentoMapper.toResponse(salvo);
    }

    /**
     * Busca um agendamento pelo ID.
     */
    @Transactional(readOnly = true)
    public AgendamentoResponse buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado: " + id));
        return agendamentoMapper.toResponse(agendamento);
    }

    /**
     * Lista todos os agendamentos paginados.
     */
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarTodos(Pageable pageable) {
        return agendamentoRepository.findAll(pageable)
                .map(agendamentoMapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> buscarAgendamentos(
            Long profissionalId,
            Long petId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable) {
        
        // Simplesmente chama o Repository e mapeia os resultados
        return agendamentoRepository.buscarPorFiltros(
                profissionalId,
                petId,
                dataInicio,
                dataFim,
                pageable
        ).map(agendamentoMapper::toResponse);
    }

    /**
     * Atualiza um agendamento existente.
     */
    @Transactional
    public AgendamentoResponse atualizar(Long id, AgendamentoRequest request) {
        Agendamento existente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado: " + id));

        // 1. Resolver Entidades 
        Pet pet = buscarPet(request.getPetId());
        Profissional profissional = buscarProfissional(request.getProfissionalId());
        Servico servico = buscarServico(request.getServicoId());

        // 2. Validações de Negócio: DELEGAÇÃO TOTAL
        agendamentoValidator.validarAgendamento(
            request.getDataHoraInicio(), 
            request.getDataHoraFim(), 
            profissional.getId(), 
            id // Passa o ID existente para ignorá-lo na verificação de conflito
        );

        // 3. Mapear e Salvar
        agendamentoMapper.updateEntityFromRequest(request, existente, pet, profissional, servico);
        Agendamento salvo = agendamentoRepository.save(existente);

        return agendamentoMapper.toResponse(salvo);
    }

    /**
     * Deleta um agendamento.
     */
    @Transactional
    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado: " + id);
        }
        agendamentoRepository.deleteById(id);
    }
    
    // --- Métodos Privados de Resolução de Entidades ---

    private Pet buscarPet(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado: " + petId));
    }

    private Profissional buscarProfissional(Long profissionalId) {
        return profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + profissionalId));
    }

    private Servico buscarServico(Long servicoId) {
        return servicoRepository.findById(servicoId)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado: " + servicoId));
    }
}