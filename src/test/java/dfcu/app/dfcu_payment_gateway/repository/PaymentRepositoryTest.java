package dfcu.app.dfcu_payment_gateway.repository;

import dfcu.app.dfcu_payment_gateway.entity.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void savePayment_ShouldPersistPayment() {
        Payment payment = Payment.builder()
                .amount(BigDecimal.valueOf(100.00))
                .currency("USD")
                .description("Test payment")
                .reference("REF123")
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        assertThat(savedPayment.getId()).isNotNull();
        assertThat(savedPayment.getAmount()).isEqualTo(payment.getAmount());
        assertThat(savedPayment.getStatus()).isEqualTo("PENDING");
    }

    @Test
    void findByReference_ShouldReturnPayment() {
        Payment payment = Payment.builder()
                .amount(BigDecimal.valueOf(100.00))
                .currency("USD")
                .description("Test payment")
                .reference("REF123")
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        Payment foundPayment = paymentRepository.findByReference("REF123").orElse(null);

        assertThat(foundPayment).isNotNull();
        assertThat(foundPayment.getReference()).isEqualTo("REF123");
    }
} 