package com.example.psychology.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "Psixoloq ID boş ola bilməz")
    private Long psychologistId;

    @NotNull(message = "Tarix boş ola bilməz")
    private LocalDate date;

    @NotNull(message = "Saat boş ola bilməz")
    private LocalTime time;
}