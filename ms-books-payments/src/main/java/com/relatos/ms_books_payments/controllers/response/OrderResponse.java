package com.relatos.ms_books_payments.controllers.response;
import com.relatos.ms_books_payments.controllers.response.OrderItemResponse;
import com.relatos.ms_books_payments.domains.Order;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private String status;
    private List<OrderItemResponse> items;

    public static @NotNull OrderResponse from(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setStatus(order.getStatus().name()); // Enum -> String
        response.setItems(order.getItems().stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toList()));
        return response;
    }
}
