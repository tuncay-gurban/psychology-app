package com.example.psychology.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePsychologistRequest {
    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Düzgün email formatı daxil edin")
    private String email;

    @NotBlank(message = "Parol boş ola bilməz")
    @Size(min = 6, message = "Parol minimum 6 simvol olmalıdır")
    private String password;

    @NotBlank(message = "Ad boş ola bilməz")
    private String name;

    @NotBlank(message = "Soyad boş ola bilməz")
    private String surname;

    @NotBlank(message = "İxtisas boş ola bilməz")
    private String specialization;

    private Integer experience;
    private String education;
    private String certificates;
    private String bio;
    private String languages;
}
