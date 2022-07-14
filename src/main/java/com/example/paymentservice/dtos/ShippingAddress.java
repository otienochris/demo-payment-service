package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingAddress{

	@JsonProperty("country_code")
	private String countryCode;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("recipient_name")
	private String recipientName;

	@JsonProperty("postal_code")
	private String postalCode;

	@JsonProperty("line1")
	private String line1;

	public String getCountryCode(){
		return countryCode;
	}

	public String getCity(){
		return city;
	}

	public String getState(){
		return state;
	}

	public String getRecipientName(){
		return recipientName;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public String getLine1(){
		return line1;
	}
}