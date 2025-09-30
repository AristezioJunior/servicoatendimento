package br.com.creedev.domain.validator;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.creedev.domain.exception.DomainRuleException;
import br.com.creedev.domain.model.Enums.StatusEntidade;
import br.com.creedev.domain.repository.AgendamentoRepository;
import br.com.creedev.domain.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgendamentoValidator {

    private final AgendamentoRepository agendamentoRepository;
    private final ProfissionalRepository profissionalRepository;
    // Repositório opcional para verificar horário de funcionamento global da clínica
    // private final ConfiguracaoRepository configuracaoRepository; 

    /**
     * Valida todas as regras de negócio para salvar ou atualizar um agendamento.
     */
    public void validarAgendamento(LocalDateTime inicio, LocalDateTime fim, Long profissionalId, Long agendamentoId) {
        
        // 1. Validação de Intervalo de Tempo e Futuro
        validarIntervalo(inicio, fim);
        validarDataNoFuturo(inicio);

        // 2. Validação de Disponibilidade do Profissional (Regra do Service)
        validarDisponibilidade(inicio, fim, profissionalId, agendamentoId);

        // 3. Validação de Profissional Ativo e Existente (Regra Adicional)
        validarProfissionalAtivo(profissionalId);
        
        // 4. Validação de Duração Mínima do Serviço (Regra Adicional)
        // Se a duração mínima do serviço for uma regra importante, implemente aqui.
    }

    // --- Regras de Negócio Adicionais ---

    private void validarIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.isAfter(fim) || inicio.isEqual(fim)) {
            throw new DomainRuleException("A data/hora de início deve ser anterior à data/hora de fim.");
        }
    }
    
    private void validarDataNoFuturo(LocalDateTime inicio) {
        // Ignora segundos para evitar problemas de arredondamento
        LocalDateTime agora = LocalDateTime.now().withSecond(0).withNano(0);
        
        if (inicio.isBefore(agora)) {
            throw new DomainRuleException("Não é possível agendar para uma data ou hora passada.");
        }
    }

    private void validarProfissionalAtivo(Long profissionalId) {
        
        // 1. Verifica se o profissional existe E se ele está com o status ATIVO
        boolean isAtivo = profissionalRepository.existsByIdAndStatus(profissionalId, StatusEntidade.ATIVO); 


        if (!isAtivo) {
            // Lança uma exceção se não for encontrado com o status ativo
            throw new DomainRuleException("O profissional selecionado (" + profissionalId + ") não está ativo ou não pode receber agendamentos.");
        }
    }

    // --- Regra Principal (Conflito) ---
    
    private void validarDisponibilidade(LocalDateTime inicio, LocalDateTime fim, Long profissionalId, Long agendamentoId) {
        boolean conflito = agendamentoRepository.hasConflict(inicio, fim, profissionalId, agendamentoId);

        if (conflito) {
            throw new DomainRuleException("O profissional já possui um agendamento conflitante no período solicitado.");
        }
    }
}