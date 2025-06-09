package com.relatos.ms_books_payments.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pagos", description = "Controlador para administrar los pagos")
public class PaymentController {

    @GetMapping
    @Operation(summary = "Consultar pagos", description = "Consulta un pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado del pago"),
            @ApiResponse(responseCode = "500", description = "Error al encontrar el pago")
    })
    public ResponseEntity<?> books() {
        try{
            return ResponseEntity.ok("Hola esto es una prueba de payments en gateway");
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

}
