package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.FaturamentoRequest;
import br.com.creedev.api.dto.response.FaturamentoResponse;
import br.com.creedev.domain.model.Agendamento;
import br.com.creedev.domain.model.Faturamento;
import br.com.creedev.domain.model.Enums.StatusPagamento;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FaturamentoMapper {

    private final AgendamentoMapper agendamentoMapper;

    public Faturamento toEntity(FaturamentoRequest request, Agendamento agendamento) {
        if (request == null) return null;

        return Faturamento.builder()
                .agendamento(agendamento)
                .dataReferencia(request.getDataReferencia())
                .valorServico(request.getValorServico())
                .desconto(request.getDesconto())
                .valorFinal(request.getValorFinal())
                .formaPagamento(request.getFormaPagamento())
                .statusPagamento(StatusPagamento.PENDENTE)
                .build();
    }

    public FaturamentoResponse toResponse(Faturamento entity) {
        if (entity == null) return null;

        return FaturamentoResponse.builder()
                .id(entity.getId())
                .agendamento(agendamentoMapper.toResponse(entity.getAgendamento()))
                .dataReferencia(entity.getDataReferencia())
                .valorServico(entity.getValorServico())
                .desconto(entity.getDesconto())
                .valorFinal(entity.getValorFinal())
                .formaPagamento(entity.getFormaPagamento())
                .statusPagamento(entity.getStatusPagamento())
                .status(entity.getStatus())
                .dataCriacao(entity.getDataCriacao())
                .dataAlteracao(entity.getDataAlteracao())
                .build();
    }
}