package com.uema.vet.controller;

import com.uema.vet.domain.dto.recepcionista.RecepcionistaRequestDTO;
import com.uema.vet.domain.dto.recepcionista.RecepcionistaResponseDTO;
import com.uema.vet.domain.entity.subclasses.Recepcionista;
import com.uema.vet.domain.entity.superclasses.Usuario;
import com.uema.vet.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recepcionistas")
public class RecepcionistaController {

    @Autowired
    private RecepcionistaService recepcionistaService;

    @GetMapping
    @PreAuthorize("hasAuthority('manager::read')")
    public ResponseEntity<List<RecepcionistaResponseDTO>> listar() {
        return ResponseEntity.ok(recepcionistaService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<RecepcionistaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                RecepcionistaResponseDTO.create(recepcionistaService.buscarPorId(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('manager::write')")
    public ResponseEntity<RecepcionistaResponseDTO> criar(@RequestBody RecepcionistaRequestDTO recepcionista) {
        Optional<RecepcionistaResponseDTO  > recepcionistaResponseDTO = recepcionistaService.criar(recepcionista);
        return recepcionistaResponseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<Void> patch(
            @RequestBody Map<String, Object> updates,
            @AuthenticationPrincipal Usuario usuario){
        if (usuario instanceof Recepcionista recepcionista) {
            recepcionistaService.patchRecepcionista(recepcionista, updates);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<RecepcionistaResponseDTO> update(@RequestBody RecepcionistaRequestDTO body,
                                                @AuthenticationPrincipal Usuario usuario) {

        if (usuario instanceof Recepcionista recepcionista) {
            Optional<RecepcionistaResponseDTO> update = recepcionistaService.updateRecepcionista(recepcionista, body);
            return update.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recepcionistaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
