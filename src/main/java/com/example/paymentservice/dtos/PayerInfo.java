package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayerInfo{
	private String countryCode;
	private String lastName;
	private String payerId;
	private ShippingAddress shippingAddress;
	private String firstName;
	private String email;
}