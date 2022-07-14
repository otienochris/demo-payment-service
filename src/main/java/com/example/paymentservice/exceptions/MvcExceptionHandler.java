package com.example.paymentservice.exceptions;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Tuesday, 05/07/2022
 */

@RestControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(PayPalRESTException.class)
    public ResponseEntity<?> payPalRESTException(PayPalRESTException payPalRESTException){
        return ResponseEntity.badRequest().body(payPalRESTException.getDetails());
    }
}
