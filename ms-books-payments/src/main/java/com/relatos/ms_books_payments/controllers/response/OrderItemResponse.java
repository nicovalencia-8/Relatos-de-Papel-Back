package com.relatos.ms_books_payments.controllers.response;

import com.relatos.ms_books_payments.domains.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponse {
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double price;

    public static OrderItemResponse from(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setBookId(Long.valueOf(item.getBookId()));
        response.setBookTitle(item.getBookTitle());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        return response;
    }
}
