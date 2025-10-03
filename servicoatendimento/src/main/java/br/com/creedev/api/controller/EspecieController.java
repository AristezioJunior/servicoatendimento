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

import br.com.creedev.api.dto.request.EspecieRequest;
import br.com.creedev.api.dto.response.EspecieResponse;
import br.com.creedev.domain.service.EspecieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/especies")
@RequiredArgsConstructor
public class EspecieController {

    private final EspecieService especieService;

    @PostMapping
    public ResponseEntity<EspecieResponse> criar(@Valid @RequestBody EspecieRequest request) {
        EspecieResponse response = especieService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecieResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especieService.buscarPorId(id));
    }

    /**
     * Busca Espécies por parte do nome, ignorando case.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<EspecieResponse>> buscarPorNome(
            @RequestParam String termo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<EspecieResponse> page = especieService.buscarPorNome(termo, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<EspecieResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<EspecieResponse> page = especieService.listarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecieResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody EspecieRequest request) {
        EspecieResponse response = especieService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        especieService.deletar(id);
    }
}