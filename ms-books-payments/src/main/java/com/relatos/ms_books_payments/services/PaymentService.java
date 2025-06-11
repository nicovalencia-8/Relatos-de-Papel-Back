package com.relatos.ms_books_payments.services;

import com.relatos.ms_books_payments.controllers.request.AddItemRequest;
import com.relatos.ms_books_payments.controllers.request.CreateOrderRequest;
import com.relatos.ms_books_payments.controllers.response.OrderResponse;
import com.relatos.ms_books_payments.domains.Order;
import com.relatos.ms_books_payments.domains.OrderItem;
import com.relatos.ms_books_payments.domains.OrderStatus;
import com.relatos.ms_books_payments.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;

    // 1. Crear nueva orden
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .userId(Long.parseLong(String.valueOf(request.getUserId())))
                .status(OrderStatus.PENDING)
                .build();

        request.getItems().forEach(item -> {
            OrderItem orderItem = OrderItem.builder()
                    .bookId(item.getBookId())
                    .id(Long.valueOf(item.getBookTitle()))
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
            order.addItem(orderItem);
        });

        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }

    // 2. Añadir ítem a orden existente
    @Transactional
    public OrderResponse addItemToOrder(Long orderId, AddItemRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (!order.isPending()) {
            throw new IllegalStateException("No se puede añadir a una orden ya pagada");
        }

        boolean itemExists = order.getItems().stream()
                .anyMatch(i -> i.getBookId().equals(request.getBookId()));

        if (itemExists) {
            throw new IllegalStateException("El libro ya está en la orden");
        }

        OrderItem orderItem = OrderItem.builder()
                .bookId(Long.valueOf(Long.parseLong(request.getBookId())))
                .bookTitle(request.getBookTitle())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        order.addItem(orderItem);
        Order updated = orderRepository.save(order);

        return OrderResponse.from(updated);
    }

    // 3. Pagar orden
    @Transactional
    public OrderResponse payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (order.isPaid()) {
            throw new IllegalStateException("La orden ya está pagada");
        }

        order.setStatus(OrderStatus.PAID);
        return OrderResponse.from(orderRepository.save(order));
    }

    // 4. Consultar órdenes por usuario
    public List<OrderResponse> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    // 5. Consultar detalle de orden
    public Optional<OrderResponse> getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::from);
    }

    // 6. Eliminar orden pendiente
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (order.isPaid()) {
            throw new IllegalStateException("No se puede eliminar una orden pagada");
        }

        orderRepository.delete(order);
    }
}
