package dfcu.app.dfcu_payment_gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test") // Don't load in test profile
@PropertySource(value = ".env", ignoreResourceNotFound = true)
public class EnvConfig {
} 