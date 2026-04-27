package com.example.psychology.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPsychologistRequest {
    @NotBlank(message = "ad bos ola bilmez")
    @Size(min = 2,max = 50,message = "Ad 2-50 simvol arasi olmalidir")
    private String name;

    @NotBlank(message = "email bos ola bilmez")
    @Email(message = "email duzgun formati duzgun deyil")
    private String email;

    @NotBlank(message = "password bos ola bilmez")
    @Size(min = 6, message = "parol en az 6 simvol olmalidir")
    private String password;
}
