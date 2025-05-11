package dfcu.app.dfcu_payment_gateway.controller;

import dfcu.app.dfcu_payment_gateway.dto.PaymentRequestDTO;
import dfcu.app.dfcu_payment_gateway.dto.PaymentResponseDTO;
import dfcu.app.dfcu_payment_gateway.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO request) {
        PaymentResponseDTO response = paymentService.initiatePayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<PaymentResponseDTO> getPaymentStatus(@PathVariable String reference) {
        PaymentResponseDTO response = paymentService.checkPaymentStatus(reference);
        return ResponseEntity.ok(response);
    }
} 