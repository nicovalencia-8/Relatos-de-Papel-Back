package com.relatos.ms_books_payments.controllers;

import com.relatos.ms_books_payments.controllers.request.CreatePaymentRequest;
import com.relatos.ms_books_payments.controllers.response.PaymentResponse;
import com.relatos.ms_books_payments.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/payments")
@Tag(name = "Pagos", description = "Controlador para administrar los pagos")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago", description = "Crea un nuevo registro de pago si el ítem es válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado o sin stock"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        try {
            PaymentResponse response = paymentService.createPayment(request);
            return ResponseEntity.status(201).body(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            log.error("Error al crear pago", ex);
            return ResponseEntity.internalServerError().body(Map.of("error", "Error interno del servidor"));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pago por ID", description = "Consulta los detalles de un pago específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(new PaymentResponse()));
    }

    @GetMapping("/user/{uid}")
    @Operation(summary = "Listar pagos por usuario", description = "Devuelve los pagos registrados por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos encontrados"),
            @ApiResponse(responseCode = "204", description = "El usuario no tiene pagos registrados"),
            @ApiResponse(responseCode = "400", description = "ID de usuario inválido"),
            @ApiResponse(responseCode = "500", description = "Error al procesar la solicitud")
    })
    public ResponseEntity<?> getPaymentsByUser(@PathVariable String uid) {
        try {
            List<PaymentResponse> responses = paymentService.getPaymentsByUser(uid);
            if (responses.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(responses);
        } catch (Exception ex) {
            log.error("Error al buscar pagos del usuario {}", uid, ex);
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al buscar los pagos del usuario"));
        }
    }
}

