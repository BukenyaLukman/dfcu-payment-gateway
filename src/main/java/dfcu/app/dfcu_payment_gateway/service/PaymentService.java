package dfcu.app.dfcu_payment_gateway.service;

import dfcu.app.dfcu_payment_gateway.dto.PaymentRequestDTO;
import dfcu.app.dfcu_payment_gateway.dto.PaymentResponseDTO;
import dfcu.app.dfcu_payment_gateway.entity.Payment;
import dfcu.app.dfcu_payment_gateway.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final Random random = new Random();

    public PaymentResponseDTO initiatePayment(PaymentRequestDTO request) {
        // Simulate minimum processing time of 100ms
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Determine transaction status based on probability
        String status = determineTransactionStatus();
        String message = getStatusMessage(status);
        int statusCode = getStatusCode(status);

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .description(request.getPayerReference())
                .reference(UUID.randomUUID().toString())
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        
        return PaymentResponseDTO.builder()
                .transactionReference(savedPayment.getReference())
                .statusCode(statusCode)
                .message(message)
                .amount(savedPayment.getAmount())
                .currency(savedPayment.getCurrency())
                .build();
    }

    private String determineTransactionStatus() {
        int probability = random.nextInt(100);
        if (probability < 10) {
            return "PENDING";  // 10% chance
        } else if (probability < 95) {
            return "SUCCESSFUL";  // 85% chance (95 - 10)
        } else {
            return "FAILED";  // 5% chance (100 - 95)
        }
    }

    private String getStatusMessage(String status) {
        return switch (status) {
            case "PENDING" -> "Transaction is pending processing";
            case "SUCCESSFUL" -> "Transaction processed successfully";
            case "FAILED" -> "Transaction processing failed";
            default -> "Unknown transaction status";
        };
    }

    private int getStatusCode(String status) {
        return switch (status) {
            case "PENDING" -> 100;
            case "SUCCESSFUL" -> 200;
            case "FAILED" -> 400;
            default -> 500;
        };
    }

    public PaymentResponseDTO checkPaymentStatus(String transactionRef) {
        Payment payment = paymentRepository.findByReference(transactionRef)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return PaymentResponseDTO.builder()
                .transactionReference(payment.getReference())
                .statusCode(getStatusCode(payment.getStatus()))
                .message(getStatusMessage(payment.getStatus()))
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .build();
    }
} 