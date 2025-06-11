package com.relatos.ms_books_payments.controllers.request;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double price;
}
