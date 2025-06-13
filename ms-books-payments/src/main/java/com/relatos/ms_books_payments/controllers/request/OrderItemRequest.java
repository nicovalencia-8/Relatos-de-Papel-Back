package com.relatos.ms_books_payments.controllers.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull
    private Long bookId;

    @Min(value = 1)
    private Integer quantity;
    private Double price;
}
