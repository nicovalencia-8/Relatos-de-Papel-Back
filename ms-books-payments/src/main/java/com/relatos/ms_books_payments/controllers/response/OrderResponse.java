package com.relatos.ms_books_payments.controllers.response;
import com.relatos.ms_books_payments.controllers.response.OrderItemResponse;
import com.relatos.ms_books_payments.domains.Order;


import com.relatos.ms_books_payments.domains.OrderStatus;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private List<OrderItemResponse> items;

    public OrderResponse (Order order) {
       this.id = order.getId();
       this.userId = order.getUserId();
       this.status = order.getStatus();
       this.items = order.getItems().stream().map(OrderItemResponse::new).collect(Collectors.toList());
    }
}
