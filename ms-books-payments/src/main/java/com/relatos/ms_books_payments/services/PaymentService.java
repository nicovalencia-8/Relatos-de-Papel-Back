package com.relatos.ms_books_payments.services;

import com.relatos.ms_books_payments.controllers.request.AddItemRequest;
import com.relatos.ms_books_payments.controllers.request.CreateOrderRequest;
import com.relatos.ms_books_payments.controllers.response.OrderResponse;
import com.relatos.ms_books_payments.domains.Order;
import com.relatos.ms_books_payments.domains.OrderItem;
import com.relatos.ms_books_payments.domains.OrderStatus;
import com.relatos.ms_books_payments.domains.OrderStatusEnum;
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
    public OrderResponse createOrder(CreateOrderRequest orderRequest) {
        Order order = new Order(orderRequest);
        Order saved = orderRepository.save(order);
        return new OrderResponse(saved);
    }

    // 2. Añadir ítem a orden existente //Para revisar
    @Transactional
    public OrderResponse addItemToOrder(Long orderId, AddItemRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (!order.getStatus().getName().equals(OrderStatusEnum.PENDING.name())) {
            throw new IllegalStateException("No se puede añadir a una orden ya pagada");
        }

        boolean itemExists = order.getItems().stream()
                .anyMatch(i -> i.getBookId().equals(request.getBookId()));

        if (itemExists) {
            throw new IllegalStateException("El libro ya está en la orden");
        }

        OrderItem orderItem = OrderItem.builder()
                .bookId(request.getBookId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        order.addItem(orderItem);
        Order updated = orderRepository.save(order);

        return new OrderResponse(updated);
    }

    // 3. Pagar orden
    @Transactional
    public OrderResponse payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (order.getStatus().getName().equals(OrderStatusEnum.PAID.name())) {
            throw new IllegalStateException("La orden ya está pagada");
        }

        order.setStatus(new OrderStatus(OrderStatusEnum.PAID.name()));
        return new OrderResponse(orderRepository.save(order));
    }

    // 4. Consultar órdenes por usuario
    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    // 5. Consultar detalle de orden
    public Optional<OrderResponse> getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::new);
    }

    // 6. Eliminar orden pendiente
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        if (order.getStatus().getName().equals(OrderStatusEnum.PAID.name())) {
            throw new IllegalStateException("No se puede eliminar una orden pagada");
        }

        orderRepository.delete(order);
    }
}
