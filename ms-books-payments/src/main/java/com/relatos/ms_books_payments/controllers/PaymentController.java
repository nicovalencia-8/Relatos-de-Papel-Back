package com.relatos.ms_books_payments.controllers;

import com.relatos.ms_books_payments.controllers.request.CreateOrderRequest;
import com.relatos.ms_books_payments.controllers.request.AddItemRequest;
import com.relatos.ms_books_payments.controllers.response.OrderResponse;
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
@RequestMapping("/api/payments/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pagos", description = "Controlador para administrar los pedidos y pagos")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Crear orden", description = "Crea un nuevo pedido (carrito) con uno o más libros")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado o sin stock")
    })
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            return ResponseEntity.status(201).body(paymentService.createOrder(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PatchMapping("/{orderId}/add-item")
    @Operation(summary = "Añadir ítem a orden", description = "Añade un nuevo libro a una orden existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítem añadido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Orden o libro no encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto de estado o ítem duplicado")
    })
    public ResponseEntity<?> addItemToOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody AddItemRequest request) {
        return ResponseEntity.ok(paymentService.addItemToOrder(orderId, request));
    }

    @PatchMapping("/{orderId}/pay")
    @Operation(summary = "Pagar orden", description = "Finaliza y paga una orden si todo está en orden")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden pagada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Orden o libro no encontrado"),
            @ApiResponse(responseCode = "409", description = "Orden ya pagada o sin stock")
    })
    public ResponseEntity<?> payOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.payOrder(orderId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Consultar órdenes de usuario", description = "Devuelve todas las órdenes de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Órdenes encontradas"),
            @ApiResponse(responseCode = "204", description = "Sin órdenes registradas")
    })
    public ResponseEntity<?> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponse> responses = paymentService.getOrdersByUser(userId);
        return responses.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(responses);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Consultar detalle de orden", description = "Consulta la información completa de una orden")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden encontrada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        return paymentService.getOrderById(orderId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Orden no encontrada")));
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Eliminar orden no pagada", description = "Elimina una orden solo si está en estado PENDING")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Orden eliminada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
            @ApiResponse(responseCode = "409", description = "Orden ya pagada")
    })
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        paymentService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
