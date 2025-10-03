package br.com.creedev.api.dto.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.ProfissionalRequest;
import br.com.creedev.api.dto.response.ProfissionalResponse;
import br.com.creedev.api.dto.response.ServicoResponse;
import br.com.creedev.domain.model.Endereco;
import br.com.creedev.domain.model.Profissional;
import br.com.creedev.domain.model.Servico;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfissionalMapper {

    private final EnderecoMapper enderecoMapper;
    private final ServicoMapper servicoMapper;

    public ProfissionalResponse toResponse(Profissional profissional) {
        if (profissional == null) return null;

        Set<ServicoResponse> servicosResp = profissional.getServicos() == null
                ? Collections.emptySet()
                : profissional.getServicos().stream()
                    .map(servicoMapper::toResponse)
                    .collect(Collectors.toSet());

        return ProfissionalResponse.builder()
                .id(profissional.getId())
                .nome(profissional.getNome())
                .especialidade(profissional.getEspecialidade())
                .telefoneFixo(profissional.getTelefoneFixo())
                .celular(profissional.getCelular())
                .email(profissional.getEmail())
                .endereco(enderecoMapper.toResponse(profissional.getEndereco()))
                .servicos(servicosResp)
                .observacoes(profissional.getObservacoes())
                .status(profissional.getStatus())
                .dataCriacao(profissional.getDataCriacao())
                .dataAlteracao(profissional.getDataAlteracao())
                .build();
    }

    /**
     * Constrói a entidade Profissional a partir do Request e das entidades relacionadas
     * já resolvidas (Endereço, Serviços).
     */
    public Profissional toEntity(ProfissionalRequest request, Endereco endereco, Set<Servico> servicos) {
        if (request == null) return null;

        Profissional p = new Profissional();
        p.setNome(request.getNome());
        p.setEspecialidade(request.getEspecialidade());
        p.setTelefoneFixo(request.getTelefoneFixo());
        p.setCelular(request.getCelular());
        p.setEmail(request.getEmail());
        p.setEndereco(endereco);
        p.setServicos(servicos == null ? new HashSet<>() : new HashSet<>(servicos));
        p.setObservacoes(request.getObservacoes());
        return p;
    }

    /**
     * Atualiza a entidade existente com os dados do request.
     * - Não zera campos que vieram nulos no request (serviço/resolução é tratada no service).
     * - Se 'servicos' for não-nulo, ele substitui o conjunto.
     */
    public void updateEntityFromRequest(ProfissionalRequest request, Profissional existente,
                                        Endereco endereco, Set<Servico> servicos) {
        if (request == null || existente == null) return;

        if (request.getNome() != null && !request.getNome().equals(existente.getNome())) {
            existente.setNome(request.getNome());
        }
        if (request.getEspecialidade() != null && !request.getEspecialidade().equals(existente.getEspecialidade())) {
            existente.setEspecialidade(request.getEspecialidade());
        }
        if (request.getTelefoneFixo() != null && !request.getTelefoneFixo().equals(existente.getTelefoneFixo())) {
            existente.setTelefoneFixo(request.getTelefoneFixo());
        }
        if (request.getCelular() != null && !request.getCelular().equals(existente.getCelular())) {
            existente.setCelular(request.getCelular());
        }
        if (request.getEmail() != null && !request.getEmail().equals(existente.getEmail())) {
            existente.setEmail(request.getEmail());
        }
        if (endereco != null) {
            existente.setEndereco(endereco);
        }
        if (servicos != null) {
            existente.setServicos(new HashSet<>(servicos));
        }
        if (request.getObservacoes() != null && !request.getObservacoes().equals(existente.getObservacoes())) {
            existente.setObservacoes(request.getObservacoes());
        }
    }
}