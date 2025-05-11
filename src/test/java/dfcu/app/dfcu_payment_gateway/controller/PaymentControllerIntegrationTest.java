package dfcu.app.dfcu_payment_gateway.controller;

import dfcu.app.dfcu_payment_gateway.dto.PaymentRequestDTO;
import dfcu.app.dfcu_payment_gateway.entity.Payment;
import dfcu.app.dfcu_payment_gateway.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void createPayment_ShouldReturnCreatedPayment() throws Exception {
        PaymentRequestDTO request = PaymentRequestDTO.builder()
                .amount(BigDecimal.valueOf(100.00))
                .currency("USD")
                .payer("1234567890")
                .payee("0987654321")
                .payerReference("REF123")
                .build();

        mockMvc.perform(post("/api/v1/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void getPayment_ShouldReturnPayment() throws Exception {
        Payment payment = paymentRepository.save(Payment.builder()
                .amount(BigDecimal.valueOf(100.00))
                .currency("USD")
                .description("Test payment")
                .reference("REF123")
                .status("PENDING")
                .build());

        mockMvc.perform(get("/api/v1/payments/" + payment.getReference()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionRef").value(payment.getReference()))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
} 