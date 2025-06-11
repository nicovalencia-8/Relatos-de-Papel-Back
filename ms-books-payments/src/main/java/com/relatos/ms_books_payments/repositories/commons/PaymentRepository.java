package com.relatos.ms_books_payments.repositories.commons;

import com.relatos.ms_books_payments.domains.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository<Payment> extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(String userId);
}
