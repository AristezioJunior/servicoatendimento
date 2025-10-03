package br.com.creedev.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/faturamentos")
@RequiredArgsConstructor
public class FaturamentoController {

    private final FaturamentoService faturamentoService;

    @PostMapping
    public ResponseEntity<FaturamentoResponse> registrar(@Valid @RequestBody FaturamentoRequest request) {
        FaturamentoResponse response = faturamentoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaturamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(faturamentoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<FaturamentoResponse>> listarTodos(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(faturamentoService.listarTodos(pageable));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FaturamentoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPagamento novoStatus) {
        return ResponseEntity.ok(faturamentoService.atualizarStatus(id, novoStatus));
    }
}