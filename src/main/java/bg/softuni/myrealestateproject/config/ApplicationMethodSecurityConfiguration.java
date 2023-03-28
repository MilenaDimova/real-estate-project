package bg.softuni.myrealestateproject.config;

import bg.softuni.myrealestateproject.service.OfferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class ApplicationMethodSecurityConfiguration {

    private final OfferService offerService;

    public ApplicationMethodSecurityConfiguration(OfferService offerService) {
        this.offerService = offerService;
    }

    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {
        return new ApplicationSecurityExpressionHandler(offerService);
    }
}
