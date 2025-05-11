package dfcu.app.dfcu_payment_gateway;

import org.springframework.boot.SpringApplication;

public class TestDfcuPaymentGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.from(DfcuPaymentGatewayApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
