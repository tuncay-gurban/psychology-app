package com.example.psychology.service;

import com.example.psychology.dto.CreatePsychologistRequest;
import com.example.psychology.entity.Psychologist;
import com.example.psychology.util.enums.Role;
import com.example.psychology.entity.User;
import com.example.psychology.exception.DuplicateResourceException;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.PsychologistRepository;
import com.example.psychology.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PsychologistRepository psychologistRepository;
    private final PasswordEncoder passwordEncoder;

    public Psychologist createPsychologist(CreatePsychologistRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Bu email artıq mövcuddur");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(Role.PSYCHOLOGIST)
                .build();

        userRepository.save(user);

        Psychologist psychologist = Psychologist.builder()
                .user(user)
                .name(request.getName())
                .surname(request.getSurname())
                .specialization(request.getSpecialization())
                .experience(request.getExperience())
                .education(request.getEducation())
                .certificates(request.getCertificates())
                .bio(request.getBio())
                .languages(request.getLanguages())
                .build();

        return psychologistRepository.save(psychologist);
    }

    public void deletePsychologist(Long id) {

        Psychologist psychologist = psychologistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psixoloq tapılmadı"));

        userRepository.delete(psychologist.getUser());
        psychologistRepository.delete(psychologist);
    }
}
