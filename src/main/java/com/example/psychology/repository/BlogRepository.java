package com.example.psychology.repository;

import com.example.psychology.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Page<Blog> findByCategory(String category, Pageable pageable);

    Page<Blog> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Blog> findByCategoryAndTitleContainingIgnoreCase(String category, String title, Pageable pageable);
}