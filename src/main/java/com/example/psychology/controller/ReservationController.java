package com.example.psychology.controller;

import com.example.psychology.dto.ReservationRequest;
import com.example.psychology.dto.ReservationResponse;
import com.example.psychology.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> getMyReservations() {
        return ResponseEntity.ok(reservationService.getMyReservations());
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<LocalTime>> getAvailableSlots(
            @RequestParam Long psychologistId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(reservationService.getAvailableSlots(psychologistId, date));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(reservationService.updateStatus(id, status));
    }
}