package com.example.psychology.controller;

import com.example.psychology.dto.VacancyRequest;
import com.example.psychology.dto.VacancyResponse;
import com.example.psychology.service.VacancyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<VacancyResponse> create(@Valid @RequestBody VacancyRequest request) {
        return ResponseEntity.ok(vacancyService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<VacancyResponse>> getAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(vacancyService.getAll(page, size, search, active));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacancyResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody VacancyRequest request) {
        return ResponseEntity.ok(vacancyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vacancyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}