package com.stockvision.controllers;

import com.stockvision.models.Holdings;
import com.stockvision.repositories.HoldingsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private HoldingsRepository holdingsRepository;

    // Fetch User Portfolio
    @GetMapping
    public ResponseEntity<?> getUserPortfolio(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        List<Holdings> portfolio = holdingsRepository.findByUserId(userId);
        return ResponseEntity.ok(portfolio);
    }
}
