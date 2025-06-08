package com.relatos.ms_books_catalogue.services;

import com.relatos.ms_books_catalogue.controllers.request.CreateAuthorRequest;
import com.relatos.ms_books_catalogue.domains.Author;
import com.relatos.ms_books_catalogue.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author createAuthor(CreateAuthorRequest authorRequest) {
        Author author = authorRepository.findByFirstNameAndLastName(authorRequest.getName(), authorRequest.getLastName());
        if (author == null) {
            author = new Author(authorRequest);
            return authorRepository.save(author);
        } else {
            throw new IllegalArgumentException("El autor ya se encuentra registrado");
        }
    }

}
