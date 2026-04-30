package com.example.psychology.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {

    @NotBlank(message = "Test başlığı boş ola bilməz")
    private String title;

    private String description;

    @NotEmpty(message = "Suallar boş ola bilməz")
    @Valid
    private List<QuestionRequest> questions;
}