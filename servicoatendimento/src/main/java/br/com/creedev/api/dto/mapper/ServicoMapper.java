package br.com.creedev.api.dto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.ServicoRequest;
import br.com.creedev.api.dto.response.ServicoResponse;
import br.com.creedev.domain.model.Servico;

@Component
public class ServicoMapper {

    public Servico toEntity(ServicoRequest request) {
        if (request == null) return null;

        Servico servico = new Servico();
        servico.setNome(request.getNome());
        servico.setDescricao(request.getDescricao());
        servico.setPrecoBase(request.getPrecoBase());
        servico.setDuracaoMinutos(request.getDuracaoMinutos());

        return servico;
    }

    public void updateEntityFromRequest(ServicoRequest request, Servico existente) {
        if (request == null || existente == null) return;

        existente.setNome(request.getNome());
        existente.setDescricao(request.getDescricao());
        existente.setPrecoBase(request.getPrecoBase());
        existente.setDuracaoMinutos(request.getDuracaoMinutos());
    }

    public ServicoResponse toResponse(Servico servico) {
        if (servico == null) return null;

        return ServicoResponse.builder()
                .id(servico.getId())
                .nome(servico.getNome())
                .descricao(servico.getDescricao())
                .precoBase(servico.getPrecoBase())
                .duracaoMinutos(servico.getDuracaoMinutos())
                .status(servico.getStatus())
                .dataCriacao(servico.getDataCriacao())
                .dataAlteracao(servico.getDataAlteracao())
                .build();
    }

    public List<ServicoResponse> toResponseList(List<Servico> servicos) {
        if (servicos == null || servicos.isEmpty()) return Collections.emptyList();
        return servicos.stream().map(this::toResponse).collect(Collectors.toList());
    }
}