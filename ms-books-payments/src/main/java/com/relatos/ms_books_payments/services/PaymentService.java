package com.relatos.ms_books_payments.services;

import com.relatos.ms_books_payments.controllers.request.CreatePaymentRequest;
import com.relatos.ms_books_payments.controllers.response.PaymentResponse;
import com.relatos.ms_books_payments.domains.Payment;
import com.relatos.ms_books_payments.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse createPayment(CreatePaymentRequest request) {
        // Aquí podrías hacer una llamada REST al microservicio de catálogo para validar itemId y stock

        // Simulación del total (por ahora harcodeado en 10.0 por ítem)
        double totalAmount = 10.0 * request.getQuantity();

        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .itemId(request.getItemId())
                .quantity(request.getQuantity())
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    public Optional<PaymentResponse> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(this::toResponse);
    }

    public List<PaymentResponse> getPaymentsByUser(String uid) {
        return paymentRepository.findByUserId(uid).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getUserId(),
                payment.getItemId(),
                payment.getQuantity(),
                payment.getTotalAmount(),
                payment.getCreatedAt()
        );
    }
}
