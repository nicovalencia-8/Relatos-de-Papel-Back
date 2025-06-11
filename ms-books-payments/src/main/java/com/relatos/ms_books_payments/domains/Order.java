package com.relatos.ms_books_payments.domains;

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
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    // Se ejecuta automáticamente antes de insertar
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Métodos útiles de estado
    public boolean isPending() {
        return OrderStatus.PENDING.equals(this.status);
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(this.status);
    }

    // Agrega un item a la orden y setea la relación bidireccional
    public void addItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        this.items.add(orderItem);
    }
}
