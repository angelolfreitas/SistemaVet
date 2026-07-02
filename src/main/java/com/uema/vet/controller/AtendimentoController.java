package com.uema.vet.controller;

import com.uema.vet.domain.dto.atendimento.AtendimentoRequest;
import com.uema.vet.domain.dto.atendimento.AtendimentoResponse;
import com.uema.vet.domain.dto.auth.LoginResponseDTO;
import com.uema.vet.domain.dto.auth.RegisterRequestDTO;
import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.superclasses.Usuario;
import com.uema.vet.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<AtendimentoResponse>> listar() {
        return ResponseEntity.ok(atendimentoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<AtendimentoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                AtendimentoResponse.create(atendimentoService.buscarPorId(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<AtendimentoResponse> criar(@RequestBody AtendimentoRequest atendimento) {
        Optional<AtendimentoResponse> novoAtendimento = atendimentoService.criar(atendimento);
        return novoAtendimento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<Void> patch(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        atendimentoService.patchAtendimento(id, updates);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<AtendimentoResponse> update(
            @PathVariable Long id,
            @RequestBody AtendimentoRequest body) {
        Optional<AtendimentoResponse> update = atendimentoService.updateAtendimento(id, body);
        return update.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atendimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}