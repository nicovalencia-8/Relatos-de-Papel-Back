package com.relatos.ms_books_payments.controllers.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OrderItemDto {
    @NotNull
    private Long bookId;
    @Min(1)
    private Integer quantity;
    @Min(1)
    private Double price;
}
