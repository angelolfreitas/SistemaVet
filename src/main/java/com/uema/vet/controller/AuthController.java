package com.uema.vet.controller;

import com.uema.vet.domain.dto.auth.LoginRequestDTO;
import com.uema.vet.domain.dto.auth.LoginResponseDTO;
import com.uema.vet.domain.dto.auth.RegisterRequestDTO;
import com.uema.vet.domain.entity.superclasses.Usuario;
import com.uema.vet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        try {
            LoginResponseDTO response = this.authService.login(body);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        try {
            LoginResponseDTO response = this.authService.register(body);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Void> patch(
            @RequestBody Map<String, Object> updates,
            @AuthenticationPrincipal Usuario user){
        authService.patchUser(user, updates);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<LoginResponseDTO> update(@RequestBody RegisterRequestDTO body,
                                       @AuthenticationPrincipal Usuario user) {

        LoginResponseDTO update = authService.updateUser(body, user);

        return ResponseEntity.ok(update);

    }
}