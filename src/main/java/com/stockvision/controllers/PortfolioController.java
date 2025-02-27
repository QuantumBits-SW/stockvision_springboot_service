package com.stockvision.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseToken;
import com.stockvision.entity.UserHolding;
import com.stockvision.services.FirebaseAuthService;
import com.stockvision.services.PortfolioService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/portfolio")
public class PortfolioController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private FirebaseAuthService firebaseAuthService;
	
	
	
	@GetMapping("/getUserPortfolio/{userId}")
	public ResponseEntity<?> getUserPortfolio(@RequestHeader("Authorization") String authToken,
			@PathVariable String userId)
	{
		if(authToken == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).
					body("Authorization header missing");
		
		try 
		{
			FirebaseToken firebaseDecodedToken = firebaseAuthService.verifyToken(authToken);
			if(firebaseDecodedToken == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
			
			List<UserHolding> userHoldings = portfolioService.getUserPortfolioDetails(userId);
			return ResponseEntity.ok(userHoldings);
			
		}
		catch (Exception e) {
			System.out.println("Error while getting portfolio : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
