package br.com.creedev.api.dto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.AgendamentoRequest;
import br.com.creedev.api.dto.response.AgendamentoResponse;
import br.com.creedev.domain.model.Agendamento;
import br.com.creedev.domain.model.Pet;
import br.com.creedev.domain.model.Profissional;
import br.com.creedev.domain.model.Servico;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgendamentoMapper {

    private final PetMapper petMapper;
    private final ProfissionalMapper profissionalMapper;
    private final ServicoMapper servicoMapper;

    public Agendamento toEntity(AgendamentoRequest request, Pet pet, Profissional profissional, Servico servico) {
        if (request == null) return null;

        Agendamento agendamento = new Agendamento();
        agendamento.setPet(pet);
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        agendamento.setDataHoraInicio(request.getDataHoraInicio());
        agendamento.setDataHoraFim(request.getDataHoraFim());
        agendamento.setStatusAgendamento(request.getStatusAgendamento());
        agendamento.setObservacao(request.getObservacao());

        return agendamento;
    }

    public void updateEntityFromRequest(AgendamentoRequest request, Agendamento existente, Pet pet, Profissional profissional, Servico servico) {
        if (request == null || existente == null) return;

        existente.setPet(pet);
        existente.setProfissional(profissional);
        existente.setServico(servico);
        existente.setDataHoraInicio(request.getDataHoraInicio());
        existente.setDataHoraFim(request.getDataHoraFim());
        existente.setStatusAgendamento(request.getStatusAgendamento());
        existente.setObservacao(request.getObservacao());
    }

    public AgendamentoResponse toResponse(Agendamento agendamento) {
        if (agendamento == null) return null;

        return AgendamentoResponse.builder()
                .id(agendamento.getId())
                .pet(petMapper.toResponse(agendamento.getPet()))
                .profissional(profissionalMapper.toResponse(agendamento.getProfissional()))
                .servico(servicoMapper.toResponse(agendamento.getServico()))
                .dataHoraInicio(agendamento.getDataHoraInicio())
                .dataHoraFim(agendamento.getDataHoraFim())
                .statusAgendamento(agendamento.getStatusAgendamento())
                .observacao(agendamento.getObservacao())
                .status(agendamento.getStatus())
                .dataCriacao(agendamento.getDataCriacao())
                .dataAlteracao(agendamento.getDataAlteracao())
                .build();
    }

    public List<AgendamentoResponse> toResponseList(List<Agendamento> agendamentos) {
        if (agendamentos == null || agendamentos.isEmpty()) return Collections.emptyList();
        return agendamentos.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
