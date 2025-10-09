package br.com.creedev.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.FaturamentoMapper;
import br.com.creedev.api.dto.request.FaturamentoRequest;
import br.com.creedev.api.dto.response.FaturamentoResponse;
import br.com.creedev.domain.exception.DomainRuleException;
import br.com.creedev.domain.model.Agendamento;
import br.com.creedev.domain.model.Faturamento;
import br.com.creedev.domain.model.Enums.StatusAgendamento;
import br.com.creedev.domain.model.Enums.StatusPagamento;
import br.com.creedev.domain.repository.AgendamentoRepository;
import br.com.creedev.domain.repository.FaturamentoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaturamentoService {

    private final FaturamentoRepository faturamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final FaturamentoMapper faturamentoMapper;

    @Transactional
    public FaturamentoResponse registrar(FaturamentoRequest request) {
        // 🔒 Verifica se o agendamento já foi faturado
        if (faturamentoRepository.existsByAgendamento_Id(request.getAgendamentoId())) {
            throw new DomainRuleException("Este agendamento já foi faturado.");
        }

        // 🔍 Busca o agendamento e valida
        Agendamento agendamento = agendamentoRepository.findById(request.getAgendamentoId())
                .orElseThrow(() -> new DomainRuleException("Agendamento não encontrado."));

        if (agendamento.getStatusAgendamento() != StatusAgendamento.CONCLUIDO) {
            throw new DomainRuleException("Somente agendamentos concluídos podem ser faturados.");
        }

        // 💰 Cálculo do valor final
        BigDecimal desconto = Optional.ofNullable(request.getDesconto()).orElse(BigDecimal.ZERO);
        BigDecimal valorFinal = request.getValorServico().subtract(desconto);
        if (valorFinal.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainRuleException("O valor final não pode ser negativo.");
        }

        // 🔨 Monta entidade usando o mapper com ambos os parâmetros
        Faturamento faturamento = faturamentoMapper.toEntity(request, agendamento);
        faturamento.setValorFinal(valorFinal);
        faturamento.setStatusPagamento(StatusPagamento.PENDENTE);

        faturamentoRepository.save(faturamento);

        return faturamentoMapper.toResponse(faturamento);
    }

    public FaturamentoResponse buscarPorId(Long id) {
        Faturamento faturamento = faturamentoRepository.findById(id)
                .orElseThrow(() -> new DomainRuleException("Faturamento não encontrado."));
        return faturamentoMapper.toResponse(faturamento);
    }

    public Page<FaturamentoResponse> listarTodos(Pageable pageable) {
        return faturamentoRepository.findAll(pageable)
                .map(faturamentoMapper::toResponse);
    }

    public List<FaturamentoResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return faturamentoRepository.buscarPorPeriodo(inicio, fim).stream()
                .map(faturamentoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FaturamentoResponse atualizarStatus(Long id, StatusPagamento novoStatus) {
        Faturamento faturamento = faturamentoRepository.findById(id)
                .orElseThrow(() -> new DomainRuleException("Faturamento não encontrado."));

        faturamento.setStatusPagamento(novoStatus);
        faturamentoRepository.save(faturamento);

        return faturamentoMapper.toResponse(faturamento);
    }

    public Map<String, Object> gerarResumo(LocalDate inicio, LocalDate fim) {
        BigDecimal total = faturamentoRepository.somarPorPeriodo(inicio, fim);
        List<Object[]> porForma = faturamentoRepository.somarPorFormaPagamento(inicio, fim);

        Map<String, BigDecimal> formas = porForma.stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (BigDecimal) arr[1]
                ));

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("dataInicio", inicio);
        resumo.put("dataFim", fim);
        resumo.put("totalFaturado", total);
        resumo.put("porFormaPagamento", formas);
        resumo.put("quantidade", faturamentoRepository.buscarPorPeriodo(inicio, fim).size());

        return resumo;
    }
}