package dfcu.app.dfcu_payment_gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI dfcuPaymentGatewayOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DFCU Payment Gateway API")
                        .description("API for processing payments and checking transaction status")
                        .version("1.0")
                        .contact(new Contact()
                                .name("DFCU Bank")
                                .email("support@dfcugroup.co.ug")));
    }
} 