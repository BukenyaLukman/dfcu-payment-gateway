package dfcu.app.dfcu_payment_gateway.repository;

import dfcu.app.dfcu_payment_gateway.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByReference(String reference);
} 