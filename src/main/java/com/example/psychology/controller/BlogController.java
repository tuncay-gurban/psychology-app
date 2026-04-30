package com.example.psychology.controller;

import com.example.psychology.dto.BlogRequest;
import com.example.psychology.dto.BlogResponse;
import com.example.psychology.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponse> create(@Valid @RequestBody BlogRequest request) {
        return ResponseEntity.ok(blogService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(blogService.getAll(page, size, category, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody BlogRequest request) {
        return ResponseEntity.ok(blogService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}