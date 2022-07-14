package com.example.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemsItem{

	@JsonProperty("quantity")
	private String quantity;

	@JsonProperty("price")
	private String price;

	@JsonProperty("name")
	private String name;

	@JsonProperty("currency")
	private String currency;

	public String getQuantity(){
		return quantity;
	}

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getCurrency(){
		return currency;
	}
}