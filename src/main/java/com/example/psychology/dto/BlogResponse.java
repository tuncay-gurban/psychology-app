package com.example.psychology.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}