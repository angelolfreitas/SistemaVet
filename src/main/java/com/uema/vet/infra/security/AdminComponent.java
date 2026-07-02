package com.uema.vet.infra.security;
import com.uema.vet.domain.dto.auth.RegisterRequestDTO;
import com.uema.vet.domain.entity.superclasses.role.Role;
import com.uema.vet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminComponent {
    @Value("${spring.security.user.name}")
    private String adminEmail;
    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initAdmin(AuthenticationService authentication) {
        return args ->
                authentication.register(
                        new RegisterRequestDTO("admin",
                                adminEmail,
                                adminPassword,
                                "",
                                Role.ADMIN),
                        Role.ADMIN
                );

    }
}