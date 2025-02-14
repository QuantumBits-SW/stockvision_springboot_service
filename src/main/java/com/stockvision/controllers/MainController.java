package com.stockvision.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stockvision")
public class MainController {

	public MainController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/")
	public String test()
	{
		return "Hello StockVision";
	}

}
