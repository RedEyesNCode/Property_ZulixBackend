package com.redeyesncode.estatespring.realestatebackend.service;


import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PaymentService {

    public PaymentIntent getPaymentIntent(String paymentMethodId) {
        try {
            return PaymentIntent.retrieve(paymentMethodId);
        } catch (StripeException e) {
            e.printStackTrace(); // Handle Stripe exception (log or throw)
            return null; // Return null in case of exception
        }
    }
    public ResponseEntity<?> createPaymentIntent(long amount, String currency,String paymentMethodId) throws StripeException {
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(amount)
                .setCurrency(currency)
//                .setPaymentMethod(paymentMethodId)

                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(createParams);

        return ResponseEntity.ok(paymentIntent.toJson());
//        return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Created Intent",paymentIntent.toJson()));
    }

    public boolean verifyPaymentMethod(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            // Check payment intent status to determine success or failure
            return "succeeded".equals(paymentIntent.getStatus());
        } catch (StripeException e) {
            e.printStackTrace(); // Handle Stripe exception (log or throw)
            return false; // Return false in case of exception
        }
    }

    public ResponseEntity<?> createPaymentMethod(HashMap<String, String> map) throws StripeException {

        PaymentMethodCreateParams params =
                PaymentMethodCreateParams.builder()
                        .setType(PaymentMethodCreateParams.Type.CARD)
                        .setCard(
                                PaymentMethodCreateParams.CardDetails.builder()
                                        .setNumber(map.get("cardNumber"))
                                        .setExpMonth(Long.valueOf(map.get("expMonth")))
                                        .setExpYear(Long.valueOf(map.get("expYear")))
                                        .setCvc(map.get("cvc"))
                                        .build()
                        )
                        .build();
        PaymentMethod paymentMethod = PaymentMethod.create(params);
        return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Success",paymentMethod.toJson()));
    }
}
