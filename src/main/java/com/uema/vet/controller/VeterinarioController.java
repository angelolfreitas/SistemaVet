package com.uema.vet.controller;

import com.uema.vet.domain.dto.atendimento.AtendimentoRequest;
import com.uema.vet.domain.dto.atendimento.AtendimentoResponse;
import com.uema.vet.domain.dto.veterinario.VeterinarioRequest;
import com.uema.vet.domain.dto.veterinario.Veterinarioresponse;
import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.subclasses.Tutor;
import com.uema.vet.domain.entity.subclasses.Veterinario;
import com.uema.vet.domain.entity.superclasses.Usuario;
import com.uema.vet.repository.projection.TopMedicamentosProjection;
import com.uema.vet.repository.projection.TotalAtendimentosProjection;
import com.uema.vet.service.VeterinarioService;
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
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<Veterinarioresponse>> listar() {
        return ResponseEntity.ok(veterinarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<Veterinarioresponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                Veterinarioresponse.create(veterinarioService.buscarPorId(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin::write')")
    public ResponseEntity<Veterinarioresponse> criar(@RequestBody VeterinarioRequest veterinario) {
        Optional<Veterinarioresponse> veterinarioresponseOptional = veterinarioService.criar(veterinario);
        return veterinarioresponseOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @PatchMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<Void> patch(
            @RequestBody Map<String, Object> updates,
            @AuthenticationPrincipal Usuario usuario){
        if (usuario instanceof Veterinario veterinario) {
            veterinarioService.patchVeterinario(veterinario, updates);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<Veterinarioresponse> update(@RequestBody VeterinarioRequest body,
                                                      @AuthenticationPrincipal Usuario usuario) {
        if (usuario instanceof Veterinario veterinario) {
            Optional<Veterinarioresponse> update = veterinarioService.updateVeterinario(veterinario, body);

            return update.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }
        return  ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/total-atendimentos")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<TotalAtendimentosProjection>> obterTotalAtendimentos() {
        return ResponseEntity.ok(veterinarioService.buscarTotalAtendimentos());
    }

    @GetMapping("/relatorio/top-medicamentos")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<TopMedicamentosProjection>> obterTopMedicamentos() {
        return ResponseEntity.ok(veterinarioService.buscarTopMedicamentosPorVeterinario());
    }
}