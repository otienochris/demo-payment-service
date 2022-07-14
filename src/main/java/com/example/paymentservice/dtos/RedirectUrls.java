package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedirectUrls{

	@JsonProperty("return_url")
	private String returnUrl;

	@JsonProperty("cancel_url")
	private String cancelUrl;

	public String getReturnUrl(){
		return returnUrl;
	}

	public String getCancelUrl(){
		return cancelUrl;
	}
}