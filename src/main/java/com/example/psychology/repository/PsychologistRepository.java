package com.example.psychology.repository;

import com.example.psychology.entity.Psychologist;
import com.example.psychology.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {

    List<Psychologist> findBySpecializationContainingIgnoreCase(String specialization);

    boolean existsByUser(User user);

    Optional<Psychologist> findByUser(User user);
}
