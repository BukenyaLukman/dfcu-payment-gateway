package dfcu.app.dfcu_payment_gateway.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponseDTO {
    private String transactionRef;
    private String transactionReference;
    private String status;
    private int statusCode;
    private String message;
    private BigDecimal amount;
    private String currency;
    private String errorMessage;
} 