package com.example.paymentservice.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("update_time")
	private String updateTime;

	@JsonProperty("create_time")
	private String createTime;

	@JsonProperty("redirect_urls")
	private RedirectUrls redirectUrls;

	@JsonProperty("links")
	private List<LinksItem> links;

	@JsonProperty("id")
	private String id;

	@JsonProperty("state")
	private String state;

	@JsonProperty("transactions")
	private List<TransactionsItem> transactions;

	@JsonProperty("intent")
	private String intent;

	@JsonProperty("payer")
	private Payer payer;

	@JsonProperty("cart")
	private String cart;

	public String getUpdateTime(){
		return updateTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public RedirectUrls getRedirectUrls(){
		return redirectUrls;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public String getId(){
		return id;
	}

	public String getState(){
		return state;
	}

	public List<TransactionsItem> getTransactions(){
		return transactions;
	}

	public String getIntent(){
		return intent;
	}

	public Payer getPayer(){
		return payer;
	}

	public String getCart(){
		return cart;
	}
}