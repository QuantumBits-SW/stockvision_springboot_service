package com.stockvision.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stockvision.entity.UserHolding;

@Repository
public interface UserHoldingRepository extends MongoRepository<UserHolding, String>{
    List<UserHolding> findByTickerIdIn(List<String> holderIds);
}
