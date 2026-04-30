package com.example.psychology.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResponse {

    private Long id;
    private String title;
    private String description;
    private List<QuestionResponse> questions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionResponse {
        private Long id;
        private String text;
        private List<AnswerResponse> answers;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerResponse {
        private Long id;
        private String text;
        private Integer score;
    }
}