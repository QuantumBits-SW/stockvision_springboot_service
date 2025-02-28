package com.stockvision.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseToken;
import com.stockvision.entity.Stock;
import com.stockvision.repository.StockRepository;
import com.stockvision.services.FinhubService;
import com.stockvision.services.FirebaseAuthService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private FinhubService finhubService;
	
	@Autowired
	private FirebaseAuthService firebaseAuthService;
	
	@GetMapping("/getAllStocks")
	public ResponseEntity<?> getAllStocks(@RequestHeader("Authorization") String authToken)
	{
		if(authToken == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).
					body("Authorization header missing");
		
		try 
		{
			FirebaseToken firebaseDecodedToken = firebaseAuthService.verifyToken(authToken);
			if(firebaseDecodedToken == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
			List<Stock> stocks = stockRepository.findAll();
			stocks = stocks.stream().map(stock ->{
				double currentPrice = finhubService.getStockPrice(stock.getStockTiker());
				stock.setCurrentPrice(currentPrice);
				return stock;
			}).toList();
			return ResponseEntity.ok(stocks);
		}
		catch (Exception e) {
			System.out.println("Error while getting portfolio : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/addStock")
	public ResponseEntity<?> addStock(@RequestBody Stock stock)
	{
		try {
			stock = stockRepository.save(stock);
			return ResponseEntity.ok(stock);
		} catch (Exception e) {
			System.out.println("Error while adding stocks : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
