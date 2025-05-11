package dfcu.app.dfcu_payment_gateway.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="payer_account", length=10, nullable=false)
    private String payerAccount;

    @Column(name="payee_account", length=10, nullable=false)
    private String payeeAccount;

    @Column(nullable=false, precision=18, scale=2)
    private BigDecimal amount;

    @Column(length=3, nullable=false)
    private String currency;

    @Column(length=255)
    private String narration;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable=false)
    private TransactionStatus status;

    @Column(name="error_message", length=500)
    private String errorMessage;

    @Column(name="created_at", nullable=false, updatable=false)
    private OffsetDateTime createdAt;

    @Column(name="last_status_check")
    private OffsetDateTime lastStatusCheck;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    // getters/setters, @PrePersist to set createdAt, etc.
}
 