package com.example.psychology.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NotBlank(message = "Cavab mətni boş ola bilməz")
    private String text;

    @NotNull(message = "Bal boş ola bilməz")
    private Integer score;
}