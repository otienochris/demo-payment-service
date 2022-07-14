package com.example.paymentservice.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsFromPaypal {
	private String id;
	private String cart;
	private String state;
	private String intent;
	private String updateTime;
	private String createTime;
	private RedirectUrls redirectUrls;
	private List<LinksItem> links;
	private List<TransactionsItem> transactions;
	private Payer payer;
}