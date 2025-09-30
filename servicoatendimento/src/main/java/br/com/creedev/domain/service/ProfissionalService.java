package br.com.creedev.domain.service;



import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.EnderecoMapper;
import br.com.creedev.api.dto.mapper.ProfissionalMapper;
import br.com.creedev.api.dto.request.ProfissionalRequest;
import br.com.creedev.api.dto.response.ProfissionalResponse;
import br.com.creedev.domain.model.Cidade;
import br.com.creedev.domain.model.Endereco;
import br.com.creedev.domain.model.Profissional;
import br.com.creedev.domain.model.Servico;
import br.com.creedev.domain.repository.CidadeRepository;
import br.com.creedev.domain.repository.ProfissionalRepository;
import br.com.creedev.domain.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ProfissionalMapper profissionalMapper;
    private final EnderecoMapper enderecoMapper;
    private final CidadeRepository cidadeRepository;
    private final ServicoRepository servicoRepository;

    @Transactional
    public ProfissionalResponse salvar(ProfissionalRequest request) {
        // validações básicas
        if (request == null) throw new IllegalArgumentException("Request não pode ser nulo");

        // resolve cidade -> endereco
        Cidade cidade = cidadeRepository.findById(request.getEndereco().getCidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + request.getEndereco().getCidadeId()));
        Endereco endereco = enderecoMapper.toEntity(request.getEndereco(), cidade);

        // resolve serviços (se informados)
        Set<Servico> servicos = resolveServicos(request.getServicoIds());

        // mapper puro recebe entidades resolvidas
        Profissional profissional = profissionalMapper.toEntity(request, endereco, servicos);

        Profissional salvo = profissionalRepository.save(profissional);
        return profissionalMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public ProfissionalResponse buscarPorId(Long id) {
        Profissional p = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + id));
        return profissionalMapper.toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<ProfissionalResponse> buscarPorNome(String termo, Pageable pageable) {
        return profissionalRepository.findByNomeContainingIgnoreCase(termo, pageable)
                .map(profissionalMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProfissionalResponse> listarTodos(Pageable pageable) {
        return profissionalRepository.findAll(pageable).map(profissionalMapper::toResponse);
    }

    @Transactional
    public ProfissionalResponse atualizar(Long id, ProfissionalRequest request) {
        Profissional existente = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + id));

        // Endereco: se veio, resolve cidade e atualiza via EnderecoMapper
        Endereco enderecoAtualizado = null;
        if (request.getEndereco() != null) {
            Cidade cidade = cidadeRepository.findById(request.getEndereco().getCidadeId())
                    .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + request.getEndereco().getCidadeId()));

            if (existente.getEndereco() == null) {
                enderecoAtualizado = enderecoMapper.toEntity(request.getEndereco(), cidade);
                existente.setEndereco(enderecoAtualizado);
            } else {
                enderecoAtualizado = existente.getEndereco();
                enderecoMapper.updateEntityFromRequest(request.getEndereco(), enderecoAtualizado, cidade);
            }
        } else {
            enderecoAtualizado = existente.getEndereco();
        }

        // Serviços: se veio, resolve e substituirá o conjunto
        Set<Servico> servicosAtualizados = null;
        if (request.getServicoIds() != null) {
            servicosAtualizados = resolveServicos(request.getServicoIds());
        }

        // Atualiza entidade via mapper puro
        profissionalMapper.updateEntityFromRequest(request, existente, enderecoAtualizado, servicosAtualizados);

        Profissional salvo = profissionalRepository.save(existente);
        return profissionalMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Profissional existente = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + id));
        profissionalRepository.delete(existente);
    }

    // --- helpers ---
    private Set<Servico> resolveServicos(Set<Long> ids) {
        if (ids == null) return null;
        if (ids.isEmpty()) return Collections.emptySet();

        List<Servico> encontrados = servicoRepository.findAllById(ids);
        if (encontrados.size() != ids.size()) {
            // identifica IDs faltantes
            Set<Long> encontradosIds = encontrados.stream().map(Servico::getId).collect(Collectors.toSet());
            Set<Long> faltantes = ids.stream().filter(id -> !encontradosIds.contains(id)).collect(Collectors.toSet());
            throw new EntityNotFoundException("Serviços não encontrados: " + faltantes);
        }
        return new HashSet<>(encontrados);
    }
}