package com.example.psychology.service;

import com.example.psychology.dto.AuthResponse;
import com.example.psychology.dto.LoginRequest;
import com.example.psychology.dto.RegisterRequest;
import com.example.psychology.util.enums.Role;
import com.example.psychology.entity.User;
import com.example.psychology.repository.UserRepository;
import com.example.psychology.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Register
     public AuthResponse register(RegisterRequest request){

         // 1. Email artiq movcuddurmu?
         if (userRepository.existsByEmail(request.getEmail())){
             throw new RuntimeException("Bu email artiq qeydiyyatdan kecib");
         }

         // 2. Yeni user yarat
         User user = User.builder()
                 .email(request.getEmail())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .name(request.getName())
                 .role(Role.USER)
                 .build();

         // 3. DB-a yaz
         userRepository.save(user);

         // 4. Token yarat
         String token = jwtService.generateToken(user.getEmail());

         // 5. Cavab qaytar
         return AuthResponse.builder()
                 .token(token)
                 .email(user.getEmail())
                 .role(user.getRole())
                 .build();
     }


     // Login
    public AuthResponse login(LoginRequest request){

         // 1. Spring Security ile autentifikasiya
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Useri DB-dan tap
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        // 3. Token yarat
        String token = jwtService.generateToken(user.getEmail());

        // 4. Cavab qaytar
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
