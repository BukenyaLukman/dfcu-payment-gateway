package dfcu.app.dfcu_payment_gateway.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
    private BigDecimal amount;
    private String currency;
    private String description;
    private String reference;
} 