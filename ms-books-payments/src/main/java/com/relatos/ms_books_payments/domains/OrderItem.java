package com.relatos.ms_books_payments.domains;

import com.relatos.ms_books_payments.controllers.request.OrderItemRequest;
import com.relatos.ms_books_payments.domains.commons.SoftEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_item")
@Builder
public class OrderItem extends SoftEntity {

    private Long bookId;
    private Integer quantity;
    private Double price;

    public OrderItem(OrderItemRequest orderItemRequest) {
        this.bookId = orderItemRequest.getBookId();
        this.quantity = orderItemRequest.getQuantity();
        this.price = orderItemRequest.getPrice();
    }
}
