package com.example.psychology.controller;

import com.example.psychology.dto.CreatePsychologistRequest;
import com.example.psychology.entity.Psychologist;
import com.example.psychology.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/psychologists")
    public ResponseEntity<String> createPsychologist(@Valid @RequestBody CreatePsychologistRequest request) {
        Psychologist psychologist = adminService.createPsychologist(request);
        return ResponseEntity.ok("Psixoloq yaradıldı: " + psychologist.getName() + " " + psychologist.getSurname());
    }

    @DeleteMapping("/psychologists/{id}")
    public ResponseEntity<String> deletePsychologist(@PathVariable Long id) {
        adminService.deletePsychologist(id);
        return ResponseEntity.ok("Psixoloq silindi");
    }
}