package com.uema.vet.controller;

import com.uema.vet.domain.dto.atendimento.AtendimentoRequest;
import com.uema.vet.domain.dto.atendimento.AtendimentoResponse;
import com.uema.vet.domain.dto.pet.PetRequest;
import com.uema.vet.domain.dto.pet.PetResponse;
import com.uema.vet.domain.entity.Atendimento;
import com.uema.vet.domain.entity.Pet;
import com.uema.vet.repository.projection.EvolucaoPesoResponse;
import com.uema.vet.repository.projection.HistoricoClinicoProjection;
import com.uema.vet.repository.projection.PetSemAtendimentoProjection;
import com.uema.vet.service.AtendimentoService;
import com.uema.vet.service.PetService;
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
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private PetService petService;

    @GetMapping
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<PetResponse>> listar() {
        return ResponseEntity.ok(petService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<PetResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                PetResponse.create(
                        petService.buscarPorId(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<PetResponse> criar(@RequestBody PetRequest pet) {

        Optional<PetResponse> petOptional = petService.criar(pet);
        return petOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<Void> patch(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        petService.patchPet(id, updates);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user::write')")
    public ResponseEntity<PetResponse> update(
            @PathVariable Long id,
            @RequestBody PetRequest body) {
        Optional<PetResponse> update = petService.updatePet(id, body);
        return update.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/historico-clinico/{idPet}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<HistoricoClinicoProjection>> obterHistoricoClinico(@PathVariable Long idPet) {
        // Para simplificar, o controller pode acessar direto o repository de atendimento injetado
        return ResponseEntity.ok(atendimentoService.buscarHistoricoClinico(idPet));
    }

    @GetMapping("/relatorio/sem-atendimento")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<PetSemAtendimentoProjection>> obterPetsSemAtendimento() {
        return ResponseEntity.ok(petService.buscarPetsSemAtendimento()); // ou via repository direto
    }
    @GetMapping("/relatorio/evolucao-peso/{idPet}")
    @PreAuthorize("hasAuthority('user::read')")
    public ResponseEntity<List<EvolucaoPesoResponse>> obterEvolucaoPeso(@PathVariable Long idPet) {
        // Para simplificar a entrega imediata do relatório REQ02, retornamos a lista nativa direta
        return ResponseEntity.ok(petService.buscarEvolucaoPesoNative(idPet));
    }
}