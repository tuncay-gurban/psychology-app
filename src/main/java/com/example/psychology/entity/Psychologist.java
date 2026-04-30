package com.example.psychology.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "psychologist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Psychologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String specialization;

    private Integer experience;

    private String education;

    private String certificates;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String languages;

    @Column(nullable = false)
    @Builder.Default
    private Double rating = 0.0;
}
