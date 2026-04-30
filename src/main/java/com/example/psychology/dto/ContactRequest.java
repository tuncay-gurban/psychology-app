package com.example.psychology.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @NotBlank(message = "Ad boş ola bilməz")
    private String name;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Düzgün email formatı daxil edin")
    private String email;

    @NotBlank(message = "Mövzu boş ola bilməz")
    private String subject;

    @NotBlank(message = "Mesaj boş ola bilməz")
    private String message;
}