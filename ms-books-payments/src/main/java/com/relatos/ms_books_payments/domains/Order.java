package com.relatos.ms_books_payments.domains;

import com.relatos.ms_books_payments.controllers.request.CreateOrderRequest;
import com.relatos.ms_books_payments.domains.commons.SoftEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
@Builder
public class Order extends SoftEntity {

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    // Agrega un item a la orden y setea la relación bidireccional
    public void addItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        this.items.add(orderItem);
    }

    public Order(CreateOrderRequest createOrderRequest) {
        this.userId = createOrderRequest.getUserId();
        this.status = new OrderStatus(OrderStatusEnum.PENDING.name());
        this.paidAt = LocalDateTime.now();
        this.items = createOrderRequest.getItems();
    }
}
