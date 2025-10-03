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

import br.com.creedev.api.dto.request.ServicoRequest;
import br.com.creedev.api.dto.response.ServicoResponse;
import br.com.creedev.domain.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoResponse> criar(@Valid @RequestBody ServicoRequest request) {
        ServicoResponse response = servicoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    /**
     * Busca Serviços por parte do nome, ignorando case.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<ServicoResponse>> buscarPorNome(
            @RequestParam String termo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ServicoResponse> page = servicoService.buscarPorNome(termo, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<ServicoResponse>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ServicoResponse> page = servicoService.listarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody ServicoRequest request) {
        ServicoResponse response = servicoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        servicoService.deletar(id);
    }
}