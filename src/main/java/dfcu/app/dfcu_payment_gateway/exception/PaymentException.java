package dfcu.app.dfcu_payment_gateway.exception;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {
    private final String errorCode;
    
    public PaymentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
} 