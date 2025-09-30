package br.com.creedev.domain.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.PetMapper;
import br.com.creedev.api.dto.request.PetRequest;
import br.com.creedev.api.dto.response.PetResponse;
import br.com.creedev.domain.model.Cliente;
import br.com.creedev.domain.model.Especie;
import br.com.creedev.domain.model.Pet;
import br.com.creedev.domain.model.Raca;
import br.com.creedev.domain.repository.ClienteRepository;
import br.com.creedev.domain.repository.EspecieRepository;
import br.com.creedev.domain.repository.PetRepository;
import br.com.creedev.domain.repository.RacaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    
    // Repositórios de entidades relacionadas
    private final EspecieRepository especieRepository;
    private final RacaRepository racaRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public PetResponse salvar(PetRequest request) {
        // 1. Resolver Entidades relacionadas
        Especie especie = buscarEspecie(request.getEspecieId());
        Raca raca = buscarRaca(request.getRacaId()).orElse(null); // Raça é opcional
        Cliente dono = buscarDono(request.getClienteId());

        // 2. Mapear, Salvar e Retornar
        Pet pet = petMapper.toEntity(request, especie, raca, dono);
        Pet salvo = petRepository.save(pet);
        return petMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public PetResponse buscarPorId(Long id) {
        Pet pet = buscarEntidade(id);
        return petMapper.toResponse(pet);
    }
    
    /**
     * Busca Pets por parte do nome, ignorando case, com suporte a paginação.
     */
    @Transactional(readOnly = true)
    public Page<PetResponse> buscarPorNome(String termo, Pageable pageable) {
        // Usa o novo método do Repository
        return petRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(petMapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<PetResponse> listarTodos(Pageable pageable) {
        return petRepository.findAll(pageable)
                .map(petMapper::toResponse);
    }

    @Transactional
    public PetResponse atualizar(Long id, PetRequest request) {
        Pet existente = buscarEntidade(id);

        // 1. Resolver Entidades relacionadas (Mesma lógica do salvar)
        Especie especie = buscarEspecie(request.getEspecieId());
        Raca raca = buscarRaca(request.getRacaId()).orElse(null);
        Cliente dono = buscarDono(request.getClienteId());

        // 2. Atualizar, Salvar e Retornar
        petMapper.updateEntityFromRequest(request, existente, especie, raca, dono);
        Pet salvo = petRepository.save(existente);
        return petMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Pet existente = buscarEntidade(id);
        petRepository.delete(existente);
    }
    
    // --- Métodos de Ajuda para Resolução ---

    private Pet buscarEntidade(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado: " + id));
    }
    
    private Especie buscarEspecie(Long especieId) {
        return especieRepository.findById(especieId)
                .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada: " + especieId));
    }

    private Optional<Raca> buscarRaca(Long racaId) {
        if (racaId == null) return Optional.empty();
        return racaRepository.findById(racaId);
                // Pode-se lançar EntityNotFoundException se Raça for obrigatória
                //.orElseThrow(() -> new EntityNotFoundException("Raça não encontrada: " + racaId));
    }

    private Cliente buscarDono(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Dono (Cliente) não encontrado: " + clienteId));
    }
}