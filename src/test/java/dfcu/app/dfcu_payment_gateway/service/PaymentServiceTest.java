package dfcu.app.dfcu_payment_gateway.service;

import dfcu.app.dfcu_payment_gateway.dto.PaymentRequestDTO;
import dfcu.app.dfcu_payment_gateway.dto.PaymentResponseDTO;
import dfcu.app.dfcu_payment_gateway.entity.Payment;
import dfcu.app.dfcu_payment_gateway.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentRequestDTO paymentRequest;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRequest = PaymentRequestDTO.builder()
                .amount(BigDecimal.valueOf(100.00))
                .currency("USD")
                .payer("1234567890")
                .payee("0987654321")
                .payerReference("REF123")
                .build();

        payment = Payment.builder()
                .id(1L)
                .amount(paymentRequest.getAmount())
                .currency(paymentRequest.getCurrency())
                .description(paymentRequest.getPayerReference())
                .reference(UUID.randomUUID().toString())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void initiatePayment_ShouldReturnPaymentResponse() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.initiatePayment(paymentRequest);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionRef()).isEqualTo(payment.getReference());
        assertThat(response.getStatus()).isEqualTo(payment.getStatus());
    }

    @Test
    void checkPaymentStatus_ShouldReturnPayment() {
        String ref = payment.getReference();
        when(paymentRepository.findByReference(ref)).thenReturn(Optional.of(payment));

        PaymentResponseDTO response = paymentService.checkPaymentStatus(ref);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionRef()).isEqualTo(payment.getReference());
    }
} 