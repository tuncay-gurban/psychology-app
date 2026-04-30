package com.example.psychology.controller;

import com.example.psychology.dto.PsychologistResponse;
import com.example.psychology.service.PsychologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/psychologists")
@RequiredArgsConstructor
public class PsychologistController {

    private final PsychologistService psychologistService;

    @GetMapping
    public ResponseEntity<List<PsychologistResponse>> getAll(
            @RequestParam(required = false) String specialization) {

        if (specialization != null && !specialization.isEmpty()) {
            return ResponseEntity.ok(psychologistService.filterBySpecialization(specialization));
        }
        return ResponseEntity.ok(psychologistService.getAllPsychologists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsychologistResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(psychologistService.getPsychologistById(id));
    }
}