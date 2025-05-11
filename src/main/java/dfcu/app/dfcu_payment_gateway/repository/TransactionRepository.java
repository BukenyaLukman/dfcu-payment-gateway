package dfcu.app.dfcu_payment_gateway.repository;

import dfcu.app.dfcu_payment_gateway.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
} 