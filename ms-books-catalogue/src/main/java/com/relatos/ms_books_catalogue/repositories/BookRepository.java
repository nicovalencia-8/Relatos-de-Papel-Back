package com.relatos.ms_books_catalogue.repositories;

import com.relatos.ms_books_catalogue.domains.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
