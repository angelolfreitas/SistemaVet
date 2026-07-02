package com.uema.vet.controller;

import com.uema.vet.domain.dto.atendimento.AtendimentoResponse;
import com.uema.vet.domain.dto.tutor.TutorRequest;
import com.uema.vet.domain.dto.tutor.TutorResponse;
import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.superclasses.Usuario;
import com.uema.vet.service.TutorService;
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
@RequestMapping("/api/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    @PreAuthorize("hasAuthority('manager::read')")
    public ResponseEntity<List<TutorResponse>> listar() {
        return ResponseEntity.ok(tutorService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<TutorResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                TutorResponse.create(tutorService.buscarPorId(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('manager::write')")
    public ResponseEntity<TutorResponse> criar(@RequestBody com.uema.vet.domain.dto.tutor.TutorRequest tutor) {
        Optional<TutorResponse> tutorResponse = tutorService.criar(tutor);
        return tutorResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<Void> patch(
            @RequestBody Map<String, Object> updates,
            @AuthenticationPrincipal Usuario usuario){
        if (usuario instanceof Tutor tutor) {
            tutorService.patchTutor(tutor, updates);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<TutorResponse> update(@RequestBody TutorRequest body,
                                                      @AuthenticationPrincipal Usuario usuario) {

        if (usuario instanceof Tutor tutor) {
            Optional<TutorResponse> update = tutorService.updateTutor(tutor, body);
            return update.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}