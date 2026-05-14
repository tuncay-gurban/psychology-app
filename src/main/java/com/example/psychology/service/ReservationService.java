package com.example.psychology.service;

import com.example.psychology.dto.ReservationRequest;
import com.example.psychology.dto.ReservationResponse;
import com.example.psychology.entity.*;
import com.example.psychology.exception.DuplicateResourceException;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.PsychologistRepository;
import com.example.psychology.repository.ReservationRepository;
import com.example.psychology.repository.UserRepository;
import com.example.psychology.util.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PsychologistRepository psychologistRepository;
    private final UserRepository userRepository;

    private static final LocalTime START_HOUR = LocalTime.of(9, 0);
    private static final LocalTime END_HOUR = LocalTime.of(17, 0);

    public ReservationResponse createReservation(ReservationRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı"));

        Psychologist psychologist = psychologistRepository.findById(request.getPsychologistId())
                .orElseThrow(() -> new ResourceNotFoundException("Psixoloq tapılmadı"));

        if (request.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Keçmiş tarixə randevu almaq olmaz");
        }

        if (request.getTime().isBefore(START_HOUR) || request.getTime().isAfter(END_HOUR.minusHours(1))) {
            throw new IllegalArgumentException("Randevu yalnız 09:00 - 16:00 arasında ola bilər");
        }

        if (reservationRepository.existsByPsychologistAndDateAndTime(
                psychologist, request.getDate(), request.getTime())) {
            throw new DuplicateResourceException("Bu vaxt artıq tutulub");
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .psychologist(psychologist)
                .date(request.getDate())
                .time(request.getTime())
                .build();

        reservationRepository.save(reservation);
        return toResponse(reservation);
    }

    public List<LocalTime> getAvailableSlots(Long psychologistId, LocalDate date) {

        Psychologist psychologist = psychologistRepository.findById(psychologistId)
                .orElseThrow(() -> new ResourceNotFoundException("Psixoloq tapılmadı"));

        List<Reservation> existing = reservationRepository.findByPsychologistAndDate(psychologist, date);

        List<LocalTime> bookedTimes = existing.stream()
                .map(Reservation::getTime)
                .collect(Collectors.toList());

        List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime current = START_HOUR;
        while (current.isBefore(END_HOUR)) {
            if (!bookedTimes.contains(current)) {
                availableSlots.add(current);
            }
            current = current.plusHours(1);
        }

        return availableSlots;
    }

    public List<ReservationResponse> getMyReservations() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı"));

        return reservationRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReservationResponse updateStatus(Long id, String status) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Randevu tapılmadı"));

        try {
            reservation.setStatus(ReservationStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Yanlış status: " + status);
        }

        reservationRepository.save(reservation);
        return toResponse(reservation);
    }

    private ReservationResponse toResponse(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .userName(r.getUser().getName())
                .userEmail(r.getUser().getEmail())
                .psychologistName(r.getPsychologist().getName())
                .psychologistSurname(r.getPsychologist().getSurname())
                .date(r.getDate())
                .time(r.getTime())
                .status(r.getStatus().name())
                .build();
    }
}