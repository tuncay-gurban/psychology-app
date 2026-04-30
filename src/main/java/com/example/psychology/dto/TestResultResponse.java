package com.example.psychology.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResultResponse {

    private String testTitle;
    private Integer totalScore;
    private Integer maxPossibleScore;
    private String riskLevel;
    private String message;
}