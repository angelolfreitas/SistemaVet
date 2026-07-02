package com.uema.vet.service;

import com.uema.vet.domain.dto.auth.LoginRequestDTO;
import com.uema.vet.domain.dto.auth.LoginResponseDTO;
import com.uema.vet.domain.dto.auth.RegisterRequestDTO;
import com.uema.vet.domain.entity.superclasses.Usuario;
import com.uema.vet.infra.security.TokenService;
import com.uema.vet.repository.UsuarioRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO data) {
        Usuario user = this.repository.findByEmail(data.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Valida se a senha está correta
        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new LoginResponseDTO(user.getUsername(), token);
        }

        throw new RuntimeException("Senha inválida.");
    }

    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO data) {
        Optional<Usuario> user = this.repository.findByEmail(data.email());

        if (user.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario newUser = new Usuario();
        newUser.setUsername(data.username());
        newUser.setEmail(data.email());
        newUser.setPassword(passwordEncoder.encode(data.password())); // Criptografa a senha
        newUser.setCpf(data.cpf());
        newUser.setRole(data.role());

        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return new LoginResponseDTO(newUser.getUsername(), token);
    }


    @Transactional
    public LoginResponseDTO updateUser(RegisterRequestDTO userDTO, Usuario user) {
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setUsername(userDTO.username());
        user.setRole(userDTO.role());
        user.setCpf(userDTO.cpf());

        this.repository.save(user);
        String newToken = tokenService.generateToken(user);
        return new LoginResponseDTO(user.getUsername(), newToken);
    }
    @Transactional
    public void patchUser(Usuario user, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Usuario.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
        });
        this.repository.save(user);
    }
}