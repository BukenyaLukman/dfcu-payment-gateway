package dfcu.app.dfcu_payment_gateway.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequestDTO {
    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Payer account number must be 10 digits")
    private String payer;

    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Payee account number must be 10 digits")
    private String payee;

    @NotNull
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "[A-Z]{3}", message = "Currency must be a valid ISO code")
    private String currency;

    private String payerReference;
} 