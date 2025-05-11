package dfcu.app.dfcu_payment_gateway.mapper;

import dfcu.app.dfcu_payment_gateway.dto.PaymentRequestDTO;
import dfcu.app.dfcu_payment_gateway.dto.PaymentResponseDTO;
import dfcu.app.dfcu_payment_gateway.entity.Transaction;
import dfcu.app.dfcu_payment_gateway.entity.TransactionStatus;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class PaymentMapper {
    
    public Transaction toEntity(PaymentRequestDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setPayerAccount(dto.getPayer());
        transaction.setPayeeAccount(dto.getPayee());
        transaction.setAmount(new BigDecimal(dto.getAmount().toString()));
        transaction.setCurrency(dto.getCurrency());
        transaction.setNarration(dto.getPayerReference());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(OffsetDateTime.now());
        return transaction;
    }

    public PaymentResponseDTO toResponseDTO(Transaction transaction) {
        int statusCode = switch (transaction.getStatus()) {
            case PENDING -> 100;
            case SUCCESSFUL -> 200;
            case FAILED -> 400;
        };

        String message = switch (transaction.getStatus()) {
            case PENDING -> "Transaction Pending";
            case SUCCESSFUL -> "Transaction successfully processed";
            case FAILED -> "Transaction failed: " + 
                (transaction.getErrorMessage() != null ? 
                transaction.getErrorMessage() : "Unknown error");
        };

        return PaymentResponseDTO.builder()
                .transactionReference(transaction.getId().toString())
                .statusCode(statusCode)
                .message(message)
                .build();
    }
} 