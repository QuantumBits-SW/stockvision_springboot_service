package com.stockvision.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FinhubService {

	private final RestTemplate restTemplate;
	
	private final ObjectMapper objectMapper;

	
	@org.springframework.beans.factory.annotation.Value("${finnhub.api.key}")
	private String finnhubAPIKey;
	
    public FinhubService(ObjectMapper objectMapper) {
    	this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
    }

	public double getStockPrice(String symbol) {
        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + finnhubAPIKey;
        try 
        {
	        String response = restTemplate.getForObject(url, String.class);
	        JsonNode jsonNode = objectMapper.readTree(response);
	        return jsonNode.get("c").asDouble();
        } catch (Exception e) {
        	return 0;
        }
    }
}
