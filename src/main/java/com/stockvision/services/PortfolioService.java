package com.stockvision.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockvision.entity.User;
import com.stockvision.entity.UserHolding;
import com.stockvision.entity.UserPortfolio;
import com.stockvision.repository.UserHoldingRepository;
import com.stockvision.repository.UserPortfolioRepository;
import com.stockvision.repository.UserRepository;

@Service
public class PortfolioService {
	@Autowired
    private UserPortfolioRepository userPortfolioRepository;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private UserHoldingRepository userHoldingRepository;
    
    @Autowired
    private FinhubService finhubService;
    
    public UserPortfolio addUserPortfolio(UserPortfolio userPortfolio) {
    	
        Optional<User> user = userRepository.findById(userPortfolio.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userPortfolio.getUserId() + " does not exist.");
        }
        return userPortfolioRepository.save(userPortfolio);
    }

    public List<UserHolding> getUserPortfolioDetails(String userId) {
        UserPortfolio portfolio = userPortfolioRepository.findByUserId(userId).get(0);

        if (portfolio == null || portfolio.getTickerId().isEmpty()) {
            return List.of(); 
        }

        List<UserHolding>  userHoldings= userHoldingRepository.findByTickerIdIn(portfolio.getTickerId());
        userHoldings = userHoldings.stream().map(holding ->{
        	double currentPrice = finhubService.getStockPrice(holding.getTickerId());
        	holding.calculatePL(currentPrice);
        	holding.setCurrentPrice(currentPrice);
        	return holding;
        }).collect(Collectors.toList());
        return userHoldings;
    }
}
