package com.example.paymentservice.services;

import com.example.paymentservice.dtos.OrderDetails;
import com.example.paymentservice.dtos.OrderDetailsFromPaypal;
import com.example.paymentservice.dtos.PaymentStatus;
import com.paypal.base.rest.PayPalRESTException;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */
public interface PaypalService {

    /**
     * This method authorizes payment
     *
     * @param orderDetails - order details
     */
    String authorizePayment(OrderDetails orderDetails) throws PayPalRESTException;

    /**
     * This method retrieves payment details
     *
     * @param paymentId - the payment id of the current pending payment
     * @return payment details
     */
    OrderDetailsFromPaypal getPaymentDetails(String paymentId) throws PayPalRESTException;

    /**
     * This method execute the actual paypal payment
     *
     * @param paymentId - the payment id from paypal
     * @param payerId   - the payer id
     * @return payment details
     */
    PaymentStatus executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
