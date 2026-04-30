package com.example.psychology.repository;

import com.example.psychology.entity.Psychologist;
import com.example.psychology.entity.Reservation;
import com.example.psychology.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByPsychologist(Psychologist psychologist);

    boolean existsByPsychologistAndDateAndTime(Psychologist psychologist, LocalDate date, LocalTime time);

    List<Reservation> findByPsychologistAndDate(Psychologist psychologist, LocalDate date);
}