package dfcu.app.dfcu_payment_gateway.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PaymentResponse {
    private String transactionReference;
    private int statusCode;
    private String message;
} 