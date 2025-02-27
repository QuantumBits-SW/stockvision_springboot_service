package com.stockvision.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("UserHoldings")
@Data
public class UserHolding {

	@Id
	private String id;
	private String tickerId;
	private double buyPrice;
	private int buyQuantity;
	
	@Transient
    private double profitLoss;
	
	@Transient
	private double currentPrice;
	
	public void calculatePL(double currentPrice) {
        this.profitLoss = (currentPrice - buyPrice) * buyQuantity;
    }
}
