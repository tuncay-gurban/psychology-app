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
public class QuestionRequest {

    @NotBlank(message = "Sual mətni boş ola bilməz")
    private String text;

    @NotEmpty(message = "Cavab variantları boş ola bilməz")
    @Valid
    private List<AnswerRequest> answers;
}