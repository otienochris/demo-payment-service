package com.example.paymentservice.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemList{

	@JsonProperty("shipping_address")
	private ShippingAddress shippingAddress;

	@JsonProperty("items")
	private List<ItemsItem> items;

	public ShippingAddress getShippingAddress(){
		return shippingAddress;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}