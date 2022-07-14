package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payer{

	@JsonProperty("payment_method")
	private String paymentMethod;

	@JsonProperty("status")
	private String status;

	@JsonProperty("payer_info")
	private PayerInfo payerInfo;

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public String getStatus(){
		return status;
	}

	public PayerInfo getPayerInfo(){
		return payerInfo;
	}
}