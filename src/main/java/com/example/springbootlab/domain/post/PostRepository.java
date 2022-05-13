package com.example.springbootlab.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.member where p.id = :id")
    Optional<Post> findByIdWithMember(Long id);
}
