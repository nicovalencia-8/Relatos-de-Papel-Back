package com.relatos.ms_books_payments.controllers.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddItemRequest {
    @NotBlank
    private String bookId;

    @Min(value = 1)
    private String bookTitle;
    private Integer quantity;
    private Double price;
}

