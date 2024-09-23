package com.sparta.spartaoutsourcing.category.repository;

import com.sparta.spartaoutsourcing.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    List<Category> findByIsDeleted(boolean isDeleted);
}
