package com.example.psychology.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "email bos ola bilmez")
    @Email(message = "email duzgun formatda olmalidir")
    private String email;

    @NotBlank(message = "Parol bos ola bilmez")
    private String password;
}

