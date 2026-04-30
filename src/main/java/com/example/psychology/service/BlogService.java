package com.example.psychology.service;

import com.example.psychology.dto.BlogRequest;
import com.example.psychology.dto.BlogResponse;
import com.example.psychology.entity.Blog;
import com.example.psychology.exception.ResourceNotFoundException;
import com.example.psychology.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogResponse create(BlogRequest request) {

        Blog blog = Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .author(request.getAuthor())
                .build();

        blogRepository.save(blog);
        return toResponse(blog);
    }

    public Page<BlogResponse> getAll(int page, int size, String category, String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Blog> blogs;

        if (category != null && search != null) {
            blogs = blogRepository.findByCategoryAndTitleContainingIgnoreCase(category, search, pageable);
        } else if (category != null) {
            blogs = blogRepository.findByCategory(category, pageable);
        } else if (search != null) {
            blogs = blogRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            blogs = blogRepository.findAll(pageable);
        }

        return blogs.map(this::toResponse);
    }

    public BlogResponse getById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bloq tapılmadı"));
        return toResponse(blog);
    }

    public BlogResponse update(Long id, BlogRequest request) {

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bloq tapılmadı"));

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setCategory(request.getCategory());
        blog.setAuthor(request.getAuthor());

        blogRepository.save(blog);
        return toResponse(blog);
    }

    public void delete(Long id) {

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bloq tapılmadı"));

        blogRepository.delete(blog);
    }

    private BlogResponse toResponse(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .category(blog.getCategory())
                .author(blog.getAuthor())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
    }
}