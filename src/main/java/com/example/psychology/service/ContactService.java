package com.example.psychology.service;

import com.example.psychology.dto.ContactRequest;
import com.example.psychology.dto.ContactResponse;
import com.example.psychology.entity.Contact;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactResponse create(ContactRequest request) {

        Contact contact = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .isRead(false)
                .build();

        contactRepository.save(contact);
        return toResponse(contact);
    }

    public List<ContactResponse> getAll() {
        return contactRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ContactResponse> getUnread() {
        return contactRepository.findByIsReadFalseOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ContactResponse markAsRead(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesaj tapılmadı"));

        contact.setIsRead(true);
        contactRepository.save(contact);
        return toResponse(contact);
    }

    public void delete(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesaj tapılmadı"));

        contactRepository.delete(contact);
    }

    private ContactResponse toResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .subject(contact.getSubject())
                .message(contact.getMessage())
                .isRead(contact.getIsRead())
                .createdAt(contact.getCreatedAt())
                .build();
    }
}