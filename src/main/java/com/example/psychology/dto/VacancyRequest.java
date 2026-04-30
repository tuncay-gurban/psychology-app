package com.example.psychology.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRequest {

    @NotBlank(message = "Başlıq boş ola bilməz")
    private String title;

    @NotBlank(message = "Təsvir boş ola bilməz")
    private String description;

    @NotBlank(message = "Məkan boş ola bilməz")
    private String location;

    @NotBlank(message = "İş növü boş ola bilməz")
    private String employmentType;

    @NotBlank(message = "Tələblər boş ola bilməz")
    private String requirements;

    private String salary;

    @NotNull(message = "Aktiv statusu boş ola bilməz")
    private Boolean active;
}