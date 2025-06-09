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
import jakarta.transaction.Transactional;
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

            Author author = validateAuthor(bookRequest.getAuthor());
            List<Category> categories = validateCategory(bookRequest.getCategory());

            Image image = new Image();
            image.setUrlImage(bookRequest.getUrlImage());
            image = imageRepository.save(image);
            book = new Book(bookRequest, image, author, categories);
            book = bookRepository.save(book);
            return new BookResponse(book);
        } else {
            throw new IllegalArgumentException("El libro ya se encuentra registrado");
        }
    }

    public BookResponse getBookById(@Param("id") Long id) {
        Book book = bookRepository.findByIdC(id);
        if (book != null){
            return new BookResponse(book);
        } else {
            throw new IllegalArgumentException("Libro no encontrado");
        }
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

    @Transactional
    public BookResponse updateBookById(@Param("id") Long bookId, CreateBookRequest bookRequest) {
        Book book = bookRepository.findByIdC(bookId);
        if (book == null){
            throw new IllegalArgumentException("Libro no encontrado");
        }
        Author author = validateAuthor(bookRequest.getAuthor());
        List<Category> categories = validateCategory(bookRequest.getCategory());

        Image image = imageRepository.findByIdC(book.getImage().getId());
        image.setUrlImage(bookRequest.getUrlImage());
        image = imageRepository.save(image);
        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setISBN(bookRequest.getISBN());
        book.setPublishedDate(bookRequest.getPublishedDate());
        book.setStock(bookRequest.getStock());
        book.setPrice(bookRequest.getPrice());
        book.setRating(bookRequest.getRating());
        book.setAuthor(author);
        book.setImage(image);
        book.setCategories(categories);
        book.setVisibility(bookRequest.getVisibility());
        book = bookRepository.save(book);
        return new BookResponse(book);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findByIdC(bookId);
        if (book != null){
            bookRepository.softDelete(bookId);
        } else {
            throw new IllegalArgumentException("Libro no encontrado");
        }
    }

    private Author validateAuthor(Long authorId) {
        Author author = authorRepository.findByIdC(authorId);
        if (author == null) {
            throw new IllegalArgumentException("El autor no existe");
        }
        return author;
    }

    private List<Category> validateCategory(List<Long> categoryId) {
        List<Category> categories = categoryRepository.findAllByIds(categoryId);
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("La categoria no existe");
        }
        return categories;
    }

}
