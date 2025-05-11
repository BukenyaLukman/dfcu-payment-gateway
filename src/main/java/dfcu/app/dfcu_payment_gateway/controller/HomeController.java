package dfcu.app.dfcu_payment_gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @Value("${app.cors.allowed-origins}")
    private String frontendUrl;

    @GetMapping("/")
    public RedirectView redirectToFrontend() {
        return new RedirectView(frontendUrl);
    }

    @GetMapping("/error")
    public RedirectView handleError() {
        return new RedirectView(frontendUrl);
    }
} 