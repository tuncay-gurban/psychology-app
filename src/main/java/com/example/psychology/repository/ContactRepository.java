package com.example.psychology.repository;

import com.example.psychology.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByIsReadFalseOrderByCreatedAtDesc();

    List<Contact> findAllByOrderByCreatedAtDesc();
}