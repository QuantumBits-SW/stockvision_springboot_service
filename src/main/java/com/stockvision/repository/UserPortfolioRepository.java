package com.stockvision.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockvision.entity.UserPortfolio;

@Repository
public interface UserPortfolioRepository extends MongoRepository<UserPortfolio, String>{
	List<UserPortfolio> findByUserId(String userId);
}
