package com.example.psychology.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacancyResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String requirements;
    private String salary;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}