package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Details{

	@JsonProperty("shipping")
	private String shipping;

	@JsonProperty("subtotal")
	private String subtotal;

	public String getShipping(){
		return shipping;
	}

	public String getSubtotal(){
		return subtotal;
	}
}