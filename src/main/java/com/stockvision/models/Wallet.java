package com.stockvision.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "wallet")
public class Wallet {
    @Id
    private String id;
    private String userId;
    private double balance;
    private List<Transaction> transactions;

    @Data
    public static class Transaction {
        private String type; // deposit or withdrawal
        private double amount;
        private String status; // pending, completed
        private Date timestamp = new Date();
    }
}
