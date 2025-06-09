package com.relatos.ms_books_catalogue.repositories;

import com.relatos.ms_books_catalogue.domains.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.categoryName) = LOWER(:name) AND c.deleted = false")
    Category findByCategoryName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE c.id IN :ids AND c.deleted = false")
    List<Category> findAllByIds(@Param("ids") List<Long> ids);

    @Query("SELECT c FROM Category c WHERE c.deleted = false AND c.id = :id")
    Category findByIdC(@Param("id") Long id);
}
