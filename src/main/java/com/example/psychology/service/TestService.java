package com.example.psychology.service;

import com.example.psychology.dto.*;
import com.example.psychology.entity.Answer;
import com.example.psychology.entity.Question;
import com.example.psychology.entity.Test;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.AnswerRepository;
import com.example.psychology.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final AnswerRepository answerRepository;

    public TestResponse createTest(TestRequest request) {

        Test test = Test.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        List<Question> questions = request.getQuestions().stream().map(qReq -> {
            Question question = Question.builder()
                    .text(qReq.getText())
                    .test(test)
                    .build();

            List<Answer> answers = qReq.getAnswers().stream().map(aReq ->
                    Answer.builder()
                            .text(aReq.getText())
                            .score(aReq.getScore())
                            .question(question)
                            .build()
            ).collect(Collectors.toList());

            question.setAnswers(answers);
            return question;
        }).collect(Collectors.toList());

        test.setQuestions(questions);
        testRepository.save(test);

        return toResponse(test);
    }

    public List<TestResponse> getAllTests() {
        return testRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TestResponse getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test tapılmadı"));
        return toResponse(test);
    }

    public TestResultResponse submitTest(Long testId, List<Long> answerIds) {

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test tapılmadı"));

        int totalScore = 0;
        for (Long answerId : answerIds) {
            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cavab tapılmadı: " + answerId));
            totalScore += answer.getScore();
        }

        int maxPossibleScore = test.getQuestions().stream()
                .mapToInt(q -> q.getAnswers().stream()
                        .mapToInt(Answer::getScore)
                        .max()
                        .orElse(0))
                .sum();

        double percentage = maxPossibleScore > 0
                ? (double) totalScore / maxPossibleScore * 100
                : 0;

        String riskLevel;
        String message;

        if (percentage <= 33) {
            riskLevel = "AŞAĞI RİSK";
            message = "Nəticəniz normaldır. Sağlam həyat tərzinizi davam etdirin.";
        } else if (percentage <= 66) {
            riskLevel = "ORTA RİSK";
            message = "Orta səviyyədə risk müəyyən edildi. Mütəxəssislə məsləhətləşmək faydalı ola bilər.";
        } else {
            riskLevel = "YÜKSƏK RİSK";
            message = "Yüksək risk müəyyən edildi. Mütəxəssisə müraciət etməyiniz tövsiyə olunur.";
        }

        return TestResultResponse.builder()
                .testTitle(test.getTitle())
                .totalScore(totalScore)
                .maxPossibleScore(maxPossibleScore)
                .riskLevel(riskLevel)
                .message(message)
                .build();
    }

    private TestResponse toResponse(Test test) {
        List<TestResponse.QuestionResponse> questions = test.getQuestions().stream().map(q ->
                TestResponse.QuestionResponse.builder()
                        .id(q.getId())
                        .text(q.getText())
                        .answers(q.getAnswers().stream().map(a ->
                                TestResponse.AnswerResponse.builder()
                                        .id(a.getId())
                                        .text(a.getText())
                                        .score(a.getScore())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build()
        ).collect(Collectors.toList());

        return TestResponse.builder()
                .id(test.getId())
                .title(test.getTitle())
                .description(test.getDescription())
                .questions(questions)
                .build();
    }
}