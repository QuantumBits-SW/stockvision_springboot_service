package com.stockvision.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockvision.services.FirebaseAuthService;

@RestController
@RequestMapping("stockvision")
public class MainController {

	@Autowired
	private FirebaseAuthService firebaseAuthService;
	public MainController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/")
	public String test()
	{
		return "Hello StockVision ";
	}
}
