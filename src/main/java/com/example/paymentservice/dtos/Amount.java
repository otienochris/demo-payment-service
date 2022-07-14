package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amount{

	@JsonProperty("total")
	private String total;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("details")
	private Details details;

	public String getTotal(){
		return total;
	}

	public String getCurrency(){
		return currency;
	}

	public Details getDetails(){
		return details;
	}
}