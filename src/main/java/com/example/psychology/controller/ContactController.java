package com.example.psychology.controller;

import com.example.psychology.dto.ContactRequest;
import com.example.psychology.dto.ContactResponse;
import com.example.psychology.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponse> create(@Valid @RequestBody ContactRequest request) {
        return ResponseEntity.ok(contactService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ContactResponse>> getUnread() {
        return ResponseEntity.ok(contactService.getUnread());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ContactResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}