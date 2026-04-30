package com.example.psychology.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    private Long id;
    private String userName;
    private String userEmail;
    private String psychologistName;
    private String psychologistSurname;
    private LocalDate date;
    private LocalTime time;
    private String status;
}