package com.example.psychology.controller;

import com.example.psychology.dto.TestRequest;
import com.example.psychology.dto.TestResponse;
import com.example.psychology.dto.TestResultResponse;
import com.example.psychology.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping
    public ResponseEntity<TestResponse> create(@Valid @RequestBody TestRequest request) {
        return ResponseEntity.ok(testService.createTest(request));
    }

    @GetMapping
    public ResponseEntity<List<TestResponse>> getAll() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<TestResultResponse> submit(
            @PathVariable Long id,
            @RequestBody List<Long> answerIds) {
        return ResponseEntity.ok(testService.submitTest(id, answerIds));
    }
}