package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payee{

	@JsonProperty("merchant_id")
	private String merchantId;

	@JsonProperty("email")
	private String email;

	public String getMerchantId(){
		return merchantId;
	}

	public String getEmail(){
		return email;
	}
}