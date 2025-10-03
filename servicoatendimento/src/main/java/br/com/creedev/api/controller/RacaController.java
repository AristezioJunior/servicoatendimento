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

import br.com.creedev.api.dto.request.RacaRequest;
import br.com.creedev.api.dto.response.RacaResponse;
import br.com.creedev.domain.service.RacaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/racas")
@RequiredArgsConstructor
public class RacaController {

    private final RacaService racaService;

    @PostMapping
    public ResponseEntity<RacaResponse> criar(@Valid @RequestBody RacaRequest request) {
        RacaResponse response = racaService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(racaService.buscarPorId(id));
    }

    /**
     * Busca Raças por parte do nome, ignorando case.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<RacaResponse>> buscarPorNome(
            @RequestParam String termo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<RacaResponse> page = racaService.buscarPorNome(termo, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<RacaResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<RacaResponse> page = racaService.listarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RacaResponse> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody RacaRequest request) {
        RacaResponse response = racaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        racaService.deletar(id);
    }
}