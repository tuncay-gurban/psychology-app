package com.example.psychology.service;

import com.example.psychology.dto.VacancyRequest;
import com.example.psychology.dto.VacancyResponse;
import com.example.psychology.entity.Vacancy;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public VacancyResponse create(VacancyRequest request) {

        Vacancy vacancy = Vacancy.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .employmentType(request.getEmploymentType())
                .requirements(request.getRequirements())
                .salary(request.getSalary())
                .active(request.getActive())
                .build();

        vacancyRepository.save(vacancy);
        return toResponse(vacancy);
    }

    public Page<VacancyResponse> getAll(int page, int size, String search, Boolean active) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Vacancy> vacancies;

        if (active != null && search != null) {
            vacancies = vacancyRepository.findByActiveAndTitleContainingIgnoreCase(active, search, pageable);
        } else if (active != null) {
            vacancies = vacancyRepository.findByActive(active, pageable);
        } else if (search != null) {
            vacancies = vacancyRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            vacancies = vacancyRepository.findAll(pageable);
        }

        return vacancies.map(this::toResponse);
    }

    public VacancyResponse getById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vakansiya tapılmadı"));
        return toResponse(vacancy);
    }

    public VacancyResponse update(Long id, VacancyRequest request) {

        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vakansiya tapılmadı"));

        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setLocation(request.getLocation());
        vacancy.setEmploymentType(request.getEmploymentType());
        vacancy.setRequirements(request.getRequirements());
        vacancy.setSalary(request.getSalary());
        vacancy.setActive(request.getActive());

        vacancyRepository.save(vacancy);
        return toResponse(vacancy);
    }

    public void delete(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vakansiya tapılmadı"));
        vacancyRepository.delete(vacancy);
    }

    private VacancyResponse toResponse(Vacancy vacancy) {
        return VacancyResponse.builder()
                .id(vacancy.getId())
                .title(vacancy.getTitle())
                .description(vacancy.getDescription())
                .location(vacancy.getLocation())
                .employmentType(vacancy.getEmploymentType())
                .requirements(vacancy.getRequirements())
                .salary(vacancy.getSalary())
                .active(vacancy.getActive())
                .createdAt(vacancy.getCreatedAt())
                .updatedAt(vacancy.getUpdatedAt())
                .build();
    }
}