package com.redeyesncode.estatespring.realestatebackend.stripe;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.api.key}")
    private String stripeApiKey;


    @Bean
    public int SetupStripe() {
        Stripe.apiKey = stripeApiKey;
        return 1;

    }
}
