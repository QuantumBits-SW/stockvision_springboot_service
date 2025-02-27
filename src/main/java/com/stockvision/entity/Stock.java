package com.stockvision.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("ListedTickers")
@Data
public class Stock {

	@Id
	private String id;
	private String stockName;
	private String stockTiker;
	
	@Transient
	private double currentPrice;
}
