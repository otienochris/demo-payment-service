package com.example.paymentservice.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionsItem{

	@JsonProperty("payee")
	private Payee payee;

	@JsonProperty("amount")
	private Amount amount;

	@JsonProperty("related_resources")
	private List<Object> relatedResources;

	@JsonProperty("item_list")
	private ItemList itemList;

	@JsonProperty("description")
	private String description;

	public Payee getPayee(){
		return payee;
	}

	public Amount getAmount(){
		return amount;
	}

	public List<Object> getRelatedResources(){
		return relatedResources;
	}

	public ItemList getItemList(){
		return itemList;
	}

	public String getDescription(){
		return description;
	}
}