package com.example.psychology.config;

import com.example.psychology.entity.Role;
import com.example.psychology.entity.User;
import com.example.psychology.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin artiq movcuddur: {}", adminEmail);
            return;
        }

        User admin = User.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .name(adminName)
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        log.info("Default admin yaradildi: {}", adminEmail);
    }
}
