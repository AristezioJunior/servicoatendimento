package br.com.creedev.api.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.creedev.api.dto.request.AgendamentoRequest;
import br.com.creedev.api.dto.response.AgendamentoResponse;
import br.com.creedev.domain.service.AgendamentoService; // Seu serviço
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    /**
     * POST /api/agendamentos : Cria um novo agendamento.
     */
    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse response = agendamentoService.salvar(request);
        // Retorna 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/agendamentos/{id} : Busca um agendamento específico pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable Long id) {
        // Retorna 200 OK
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }
    
    // NOTA: Não incluí o método 'buscar' por termo, pois geralmente agendamentos
    // são buscados por data, profissional ou pet, o que requereria um endpoint
    // e método de Service/Repository mais complexo. Mantive o padrão de listar todos.

    /**
     * GET /api/agendamentos : Lista todos os agendamentos paginados.
     */
    @GetMapping
    public ResponseEntity<Page<AgendamentoResponse>> listarTodos(
            // Padrão de paginação: 10 itens por página, ordenado pelo ID
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<AgendamentoResponse> page = agendamentoService.listarTodos(pageable);
        // Retorna 200 OK
        return ResponseEntity.ok(page);
    }
    
    /**
     * GET /api/agendamentos/buscar : Busca agendamentos por filtros (Profissional, Pet, Período).
     * Todos os filtros são opcionais.
     */
    //EXEMPLO DE USO:
    // /api/agendamentos/buscar?profissionalId=2&dataInicio=2024-12-01T00:00:00
    // /api/agendamentos/buscar?dataInicio=2024-12-01T00:00:00&dataFim=2024-12-31T23:59:59
    ///api/agendamentos/buscar?profissionalId=2
    ////api/agendamentos/buscar?petId=5
    @GetMapping("/buscar")
    public ResponseEntity<Page<AgendamentoResponse>> buscarAgendamentos( @RequestParam(required = false) Long profissionalId, @RequestParam(required = false) Long petId, @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio, @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault(size = 10, sort = "dataHoraInicio") Pageable pageable) {

        Page<AgendamentoResponse> page = agendamentoService.buscarAgendamentos(
                profissionalId,
                petId,
                dataInicio,
                dataFim,
                pageable
        );
        return ResponseEntity.ok(page);
    }

    /**
     * PUT /api/agendamentos/{id} : Atualiza um agendamento existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse response = agendamentoService.atualizar(id, request);
        // Retorna 200 OK
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/agendamentos/{id} : Deleta um agendamento.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 No Content
    public void deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
    }
}