package com.relatos.ms_books_payments.controllers.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    private Long bookId;
    private Integer quantity;
    private Double price;
}
