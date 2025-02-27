package com.stockvision.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockvision.entity.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String>{

}
