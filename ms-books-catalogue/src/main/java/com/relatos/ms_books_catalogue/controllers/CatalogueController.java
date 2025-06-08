package com.relatos.ms_books_catalogue.controllers;

import com.relatos.ms_books_catalogue.controllers.request.CreateBookRequest;
import com.relatos.ms_books_catalogue.controllers.response.BookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/catalogue")
@Tag(name = "Catalogo", description = "Controlador para administrar el catalogo")
public class CatalogueController {

    @PostMapping
    @Operation(summary = "Crear libros", description = "Crea un libro en el catalogo y retorna el libro creado como repsuesta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro creado"),
            @ApiResponse(responseCode = "500", description = "Error al crear el libro")
    })
    public BookResponse books(@RequestBody @Valid CreateBookRequest createBookRequest) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setTitle(createBookRequest.getTitle());
        bookResponse.setDescription(createBookRequest.getDescription());
        bookResponse.setCategory(createBookRequest.getCategory());
        bookResponse.setStock(createBookRequest.getStock());
        bookResponse.setId(1L);
        bookResponse.setAuthor(createBookRequest.getAuthor().getName() + " " + createBookRequest.getAuthor().getLastName());
        bookResponse.setPrice(createBookRequest.getPrice());
        bookResponse.setISBN(createBookRequest.getISBN());
        bookResponse.setImg_url(createBookRequest.getImage());
        return bookResponse;
    }

}
