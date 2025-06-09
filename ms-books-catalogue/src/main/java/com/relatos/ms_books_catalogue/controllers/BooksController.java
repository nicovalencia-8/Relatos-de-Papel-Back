package com.relatos.ms_books_catalogue.controllers;

import com.relatos.ms_books_catalogue.controllers.request.CreateBookRequest;
import com.relatos.ms_books_catalogue.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/books")
@Tag(name = "Libros", description = "Controlador para administrar los libros")
public class BooksController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Crear libros", description = "Crea un libro en el catalogo y retorna el libro creado como respuesta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro creado"),
            @ApiResponse(responseCode = "500", description = "Error al crear el libro")
    })
    public ResponseEntity<?> books(@RequestBody @Valid CreateBookRequest bookRequest) {
        try{
            return ResponseEntity.ok(bookService.createBook(bookRequest));
        } catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Consultar libro", description = "Consulta un libro en el catalogo por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado del libro"),
            @ApiResponse(responseCode = "500", description = "Error al encontrar el libro")
    })
    public ResponseEntity<?> books(@PathVariable Long bookId) {
        try{
            return ResponseEntity.ok(bookService.getBookById(bookId));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Consultar libros", description = "Consulta un libro en el catalogo por filtros (titulo, autor, fecha de publicacion, categoria, ISBN, valoración, visibilidad)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado del libro"),
            @ApiResponse(responseCode = "500", description = "Error al encontrar el libro")
    })
    public ResponseEntity<?> books(@RequestParam(required = false) String title,
                                   @RequestParam(required = false) Long authorId,
                                   @RequestParam(required = false) LocalDateTime publishedDate,
                                   @RequestParam(required = false) Long categoryId,
                                   @RequestParam(required = false) String isbn,
                                   @RequestParam(required = false) Double rating,
                                   @RequestParam(required = false) Boolean visibility,
                                   @RequestParam int page,
                                   @RequestParam int size) {
        try{
            return ResponseEntity.ok(bookService.getAllBooksByFilter(title, authorId, publishedDate, categoryId, isbn, rating, visibility, page, size));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Actualizar libro", description = "Actualiza el libro por ID en el catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el libro actualizado en la respuesta"),
            @ApiResponse(responseCode = "500", description = "Error al actualizar el libro")
    })
    public ResponseEntity<?> books(@PathVariable Long bookId, @RequestBody @Valid CreateBookRequest bookRequest) {
        try{
            return ResponseEntity.ok(bookService.updateBookById(bookId, bookRequest));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Eliminar libro", description = "Elimina el libro por ID en el catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Libro")
    })
    public ResponseEntity<?> deleteBooks(@PathVariable Long bookId) {
        try{
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

}
