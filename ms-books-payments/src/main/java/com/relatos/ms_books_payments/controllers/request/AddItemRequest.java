package com.relatos.ms_books_payments.controllers.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddItemRequest {
    @NotBlank
    private Long bookId;

    @Min(value = 1)
    private Integer quantity;
    private Double price;
}

