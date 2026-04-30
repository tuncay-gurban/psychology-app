package com.example.psychology.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {

    @NotBlank(message = "Başlıq boş ola bilməz")
    private String title;

    @NotBlank(message = "Mətn boş ola bilməz")
    private String content;

    @NotBlank(message = "Kateqoriya boş ola bilməz")
    private String category;

    @NotBlank(message = "Müəllif adı boş ola bilməz")
    private String author;
}