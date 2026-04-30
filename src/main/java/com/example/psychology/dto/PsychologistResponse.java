package com.example.psychology.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PsychologistResponse {
    private Long id;
    private String name;
    private String surname;
    private String specialization;
    private Integer experience;
    private String education;
    private String certificates;
    private String bio;
    private String languages;
    private Double rating;
    private String email;
}
