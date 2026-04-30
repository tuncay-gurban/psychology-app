package com.example.psychology.service;

import com.example.psychology.dto.PsychologistResponse;
import com.example.psychology.entity.Psychologist;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.PsychologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PsychologistService {

    private final PsychologistRepository psychologistRepository;

    public List<PsychologistResponse> getAllPsychologists() {
        return psychologistRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PsychologistResponse getPsychologistById(Long id) {
        Psychologist psychologist = psychologistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psixoloq tapılmadı"));
        return toResponse(psychologist);
    }

    public List<PsychologistResponse> filterBySpecialization(String specialization) {
        return psychologistRepository.findBySpecializationContainingIgnoreCase(specialization)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PsychologistResponse toResponse(Psychologist p) {
        return PsychologistResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .surname(p.getSurname())
                .specialization(p.getSpecialization())
                .experience(p.getExperience())
                .education(p.getEducation())
                .certificates(p.getCertificates())
                .bio(p.getBio())
                .languages(p.getLanguages())
                .rating(p.getRating())
                .email(p.getUser().getEmail())
                .build();
    }
}