package com.example.psychology.repository;

import com.example.psychology.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    Page<Vacancy> findByActive(Boolean active, Pageable pageable);

    Page<Vacancy> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Vacancy> findByActiveAndTitleContainingIgnoreCase(Boolean active, String title, Pageable pageable);

    Page<Vacancy> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}