package br.com.creedev.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.FaturamentoMapper;
import br.com.creedev.api.dto.request.FaturamentoRequest;
import br.com.creedev.api.dto.response.FaturamentoResponse;
import br.com.creedev.domain.exception.BusinessException;
import br.com.creedev.domain.model.Agendamento;
import br.com.creedev.domain.model.Faturamento;
import br.com.creedev.domain.model.Enums.StatusAgendamento;
import br.com.creedev.domain.model.Enums.StatusPagamento;
import br.com.creedev.domain.repository.AgendamentoRepository;
import br.com.creedev.domain.repository.FaturamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaturamentoService {

    private final AgendamentoRepository agendamentoRepository;

    private final FaturamentoRepository faturamentoRepository;
    private final FaturamentoMapper faturamentoMapper;


    @Transactional
    public FaturamentoResponse registrar(FaturamentoRequest request) {
        Agendamento agendamento = buscarAgendamento(request.getAgendamentoId());

        if (agendamento.getStatusAgendamento() != StatusAgendamento.CONCLUIDO) {
            throw new BusinessException("Só é possível faturar agendamentos concluídos.");
        }

        Faturamento faturamento = faturamentoMapper.toEntity(request, agendamento);
        Faturamento salvo = faturamentoRepository.save(faturamento);

        return faturamentoMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public FaturamentoResponse buscarPorId(Long id) {
        return faturamentoMapper.toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public Page<FaturamentoResponse> listarTodos(Pageable pageable) {
        return faturamentoRepository.findAll(pageable).map(faturamentoMapper::toResponse);
    }

    @Transactional
    public FaturamentoResponse atualizarStatus(Long id, StatusPagamento novoStatus) {
        Faturamento faturamento = buscarEntidade(id);
        faturamento.setStatusPagamento(novoStatus);
        return faturamentoMapper.toResponse(faturamentoRepository.save(faturamento));
    }
    
    // --- Métodos Privados de Resolução de Entidades ---

    private Agendamento buscarAgendamento(Long agendamentoId) {
        return agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado: " + agendamentoId));
    }

    public Faturamento buscarEntidade(Long id) {
        return faturamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Faturamento não encontrado: " + id));
    }
}