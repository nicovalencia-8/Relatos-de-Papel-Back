package com.relatos.ms_books_payments.controllers.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Long userId;
    private List<OrderItemDto> items;

    @Data
    public static class OrderItemDto {
        private Long bookId;
        private String bookTitle;
        private Integer quantity;
        private Double price;
    }
}
