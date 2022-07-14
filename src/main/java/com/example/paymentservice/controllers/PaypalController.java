package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.OrderDetails;
import com.example.paymentservice.dtos.OrderDetailsFromPaypal;
import com.example.paymentservice.dtos.PaymentStatus;
import com.example.paymentservice.models.UrlLink;
import com.example.paymentservice.services.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/paypal/payment")
public class PaypalController {

    private final PaypalService paypalService;

    @Value("${paypal.redirect.cancel}")
    private String paymentCancellationRedirectUrl;
    @Value("${paypal.redirect.return}")
    private String paymentReviewPageUrl;
    @Value("${paypal.redirect.success}")
    private String successfulPaymentRedirectUrl;

    @PostMapping(value = "/authorize", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UrlLink> authorizePayment(@RequestBody @Validated OrderDetails orderDetails) throws PayPalRESTException {
        String link = paypalService.authorizePayment(orderDetails);
        return ResponseEntity.ok(UrlLink.builder().link(link).build());
    }


    @GetMapping(value = "/review/{paymentId}")
    public ResponseEntity<OrderDetailsFromPaypal> getOrderDetails(@PathVariable String paymentId) throws PayPalRESTException {
        return ResponseEntity.ok(paypalService.getPaymentDetails(paymentId));
    }

    @GetMapping(value = "/cancellation")
    public RedirectView paymentUnAuthorized() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(paymentCancellationRedirectUrl);
        return redirectView;
    }

    @GetMapping("/execute-payment/{paymentId}/{payerId}")
    public ResponseEntity<PaymentStatus> executePayment(@PathVariable String paymentId, @PathVariable String payerId) throws PayPalRESTException {
        return ResponseEntity.ok(paypalService.executePayment(paymentId, payerId));
    }
}
