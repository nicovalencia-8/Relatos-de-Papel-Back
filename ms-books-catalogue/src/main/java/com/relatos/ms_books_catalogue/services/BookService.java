package com.relatos.ms_books_catalogue.services;

import com.relatos.ms_books_catalogue.controllers.request.CreateBookRequest;
import com.relatos.ms_books_catalogue.controllers.response.BookResponse;
import com.relatos.ms_books_catalogue.controllers.response.commons.PageResponse;
import com.relatos.ms_books_catalogue.domains.Author;
import com.relatos.ms_books_catalogue.domains.Book;
import com.relatos.ms_books_catalogue.domains.Category;
import com.relatos.ms_books_catalogue.domains.Image;
import com.relatos.ms_books_catalogue.repositories.AuthorRepository;
import com.relatos.ms_books_catalogue.repositories.BookRepository;
import com.relatos.ms_books_catalogue.repositories.CategoryRepository;
import com.relatos.ms_books_catalogue.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;

    public BookResponse createBook(CreateBookRequest bookRequest) {
        Book book = bookRepository.findByISBN(bookRequest.getISBN());
        if (book == null) {
            Author author = authorRepository.findByIdC(bookRequest.getAuthor());
            Image image = new Image();
            image.setUrlImage(bookRequest.getImage());
            image = imageRepository.save(image);
            List<Category> categories = categoryRepository.findAllByIds(bookRequest.getCategory());
            book = new Book(bookRequest, image, author, categories);
            book = bookRepository.save(book);
            return new BookResponse(book);
        } else {
            throw new IllegalArgumentException("El libro ya se encuentra registrado");
        }
    }

    public BookResponse getBookById(@Param("id") Long id) {
        Book book = bookRepository.findByIdC(id);
        return new BookResponse(book);
    }

    public PageResponse<BookResponse> getAllBooksByFilter(String title,
                                                          Long authorId,
                                                          LocalDateTime publishedDate,
                                                          Long categoryId,
                                                          String isbn,
                                                          Double rating,
                                                          Boolean visibility,
                                                          int page,
                                                          int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Book> pageBook = bookRepository.findAllByFilters(pageRequest, title, authorId, publishedDate, categoryId, isbn, rating, visibility);
        return new PageResponse<>(pageBook.map(BookResponse::new));
    }

}
