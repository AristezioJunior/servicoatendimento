package br.com.creedev.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.creedev.api.dto.request.EstadoRequest;
import br.com.creedev.api.dto.response.EstadoResponse;
import br.com.creedev.domain.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @PostMapping
    public ResponseEntity<EstadoResponse> criar(@Valid @RequestBody EstadoRequest request) {
        EstadoResponse response = estadoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.buscarPorId(id));
    }

    /**
     * Busca Estados por parte do nome, ignorando case.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<EstadoResponse>> buscarPorNome(
            @RequestParam String termo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<EstadoResponse> page = estadoService.buscarPorNome(termo, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<EstadoResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<EstadoResponse> page = estadoService.listarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody EstadoRequest request) {
        EstadoResponse response = estadoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        estadoService.deletar(id);
    }
}