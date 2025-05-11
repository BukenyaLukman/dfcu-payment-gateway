package dfcu.app.dfcu_payment_gateway.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private String status;
    private BigDecimal amount;
    private String currency;
} 