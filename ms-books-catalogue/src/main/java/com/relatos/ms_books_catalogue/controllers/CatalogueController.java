package com.relatos.ms_books_catalogue.controllers;

import com.relatos.ms_books_catalogue.controllers.response.BookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
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
    public BookResponse books(){
        return new BookResponse();
    }

}
