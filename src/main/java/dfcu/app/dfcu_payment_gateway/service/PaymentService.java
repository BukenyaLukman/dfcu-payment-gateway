package dfcu.app.dfcu_payment_gateway.service;

import dfcu.app.dfcu_payment_gateway.dto.PaymentRequestDTO;
import dfcu.app.dfcu_payment_gateway.dto.PaymentResponseDTO;
import dfcu.app.dfcu_payment_gateway.entity.Payment;
import dfcu.app.dfcu_payment_gateway.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentResponseDTO initiatePayment(PaymentRequestDTO request) {
        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .description(request.getPayerReference())
                .reference(UUID.randomUUID().toString())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResponseDTO.builder()
                .transactionRef(savedPayment.getReference())
                .status(savedPayment.getStatus())
                .amount(savedPayment.getAmount())
                .build();
    }

    public PaymentResponseDTO checkPaymentStatus(String transactionRef) {
        Payment payment = paymentRepository.findByReference(transactionRef)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaymentResponseDTO.builder()
                .transactionRef(payment.getReference())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .build();
    }
} 