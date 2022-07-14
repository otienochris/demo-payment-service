package com.example.paymentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {
    private String countryCode;
    private String city;
    private String state;
    private String recipientName;
    private String postalCode;
    private String line1;
}