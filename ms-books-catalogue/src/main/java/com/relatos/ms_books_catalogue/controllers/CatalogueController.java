package com.relatos.ms_books_catalogue.controllers;

import com.relatos.ms_books_catalogue.controllers.request.CreateAuthorRequest;
import com.relatos.ms_books_catalogue.controllers.request.CreateBookRequest;
import com.relatos.ms_books_catalogue.controllers.request.CreateCategoryRequest;
import com.relatos.ms_books_catalogue.services.AuthorService;
import com.relatos.ms_books_catalogue.services.BookService;
import com.relatos.ms_books_catalogue.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Catalogo", description = "Controlador para administrar el catalogo")
public class CatalogueController {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @PostMapping("/books")
    @Operation(summary = "Crear libros", description = "Crea un libro en el catalogo y retorna el libro creado como repsuesta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro creado"),
            @ApiResponse(responseCode = "500", description = "Error al crear el libro")
    })
    public ResponseEntity<?> books(@RequestBody @Valid CreateBookRequest bookRequest) {
        try{
            return ResponseEntity.ok(bookService.createBook(bookRequest));
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/categories")
    @Operation(summary = "Crear categorias", description = "Crea una categoria en el catalogo y retorna la categoria creada como repsuesta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria creada"),
            @ApiResponse(responseCode = "500", description = "Error al crear la categoria")
    })
    public ResponseEntity<?> categories(@RequestBody @Valid CreateCategoryRequest categoryRequest) {
        try{
            return ResponseEntity.ok(categoryService.createCategories(categoryRequest));
        }catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/authors")
    @Operation(summary = "Crear autores", description = "Crea un autor en el catalogo y retorna el autor creada como repsuesta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor creado"),
            @ApiResponse(responseCode = "500", description = "Error al crear el autor")
    })
    public ResponseEntity<?> authors(@RequestBody @Valid CreateAuthorRequest authorRequest) {
        try{
            return ResponseEntity.ok(authorService.createAuthor(authorRequest));
        }catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

}
