package dfcu.app.dfcu_payment_gateway.exception;

import dfcu.app.dfcu_payment_gateway.dto.PaymentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<PaymentResponseDTO> handlePaymentException(PaymentException ex) {
        PaymentResponseDTO response = PaymentResponseDTO.builder()
                .transactionRef(UUID.randomUUID().toString())
                .statusCode(400)
                .message("Transaction failed: " + ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PaymentResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        PaymentResponseDTO response = PaymentResponseDTO.builder()
                .transactionReference(UUID.randomUUID().toString())
                .statusCode(400)
                .message("Transaction failed: " + errorMessage)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
} 