package com.relatos.ms_books_payments.controllers.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String itemId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
