package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayerInfo{

	@JsonProperty("country_code")
	private String countryCode;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("payer_id")
	private String payerId;

	@JsonProperty("shipping_address")
	private ShippingAddress shippingAddress;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("email")
	private String email;

	public String getCountryCode(){
		return countryCode;
	}

	public String getLastName(){
		return lastName;
	}

	public String getPayerId(){
		return payerId;
	}

	public ShippingAddress getShippingAddress(){
		return shippingAddress;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}
}