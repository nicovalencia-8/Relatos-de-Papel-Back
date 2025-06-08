package com.relatos.ms_books_catalogue.repositories;

import com.relatos.ms_books_catalogue.domains.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE LOWER(a.firstName) = LOWER(:firstName) AND LOWER(a.lastName) = LOWER(:lastName) AND a.deleted = false")
    Author findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT a FROM Author a WHERE a.id = :id AND a.deleted = false")
    Author findByIdC(@Param("id") Long id);
}
