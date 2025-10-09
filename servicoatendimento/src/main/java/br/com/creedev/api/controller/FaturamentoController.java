package br.com.creedev.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.creedev.api.dto.request.FaturamentoRequest;
import br.com.creedev.api.dto.response.FaturamentoResponse;
import br.com.creedev.domain.model.Enums.StatusPagamento;
import br.com.creedev.domain.service.FaturamentoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/faturamentos")
@RequiredArgsConstructor
public class FaturamentoController {

    private final FaturamentoService faturamentoService;

    @PostMapping
    @Operation(summary = "Registrar novo faturamento", method = "POST")
    public ResponseEntity<FaturamentoResponse> registrar(@RequestBody FaturamentoRequest request) {
        FaturamentoResponse response = faturamentoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar faturamento por ID", method = "GET")
    public ResponseEntity<FaturamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(faturamentoService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar faturamentos com paginação", method = "GET")
    public ResponseEntity<Page<FaturamentoResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "dataReferencia", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(faturamentoService.listarTodos(pageable));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar faturamentos por período", method = "GET")
    public ResponseEntity<List<FaturamentoResponse>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(faturamentoService.buscarPorPeriodo(inicio, fim));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status de pagamento do faturamento", method = "PATCH")
    public ResponseEntity<FaturamentoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPagamento novoStatus) {
        return ResponseEntity.ok(faturamentoService.atualizarStatus(id, novoStatus));
    }

    @GetMapping("/resumo")
    @Operation(summary = "Gerar resumo de faturamento no período", method = "GET")
    public ResponseEntity<Map<String, Object>> gerarResumo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(faturamentoService.gerarResumo(inicio, fim));
    }
}