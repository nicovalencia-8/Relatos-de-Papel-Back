package com.relatos.ms_books_catalogue.repositories;

import com.relatos.ms_books_catalogue.domains.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.ISBN) = LOWER(:isbn)")
    Book findByISBN(@Param("isbn") String isbn);
}
