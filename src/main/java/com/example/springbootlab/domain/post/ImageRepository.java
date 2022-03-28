package com.example.springbootlab.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Post> {
}
