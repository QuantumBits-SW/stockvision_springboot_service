package com.stockvision.repositories;

import com.stockvision.models.Holdings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldingsRepository extends MongoRepository<Holdings, String> {
    List<Holdings> findByUserId(String userId);
}
