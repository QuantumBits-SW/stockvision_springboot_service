package com.stockvision.controllers;

<<<<<<< HEAD
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stockvision.models.Wallet;
import com.stockvision.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
=======
import com.stockvision.models.Transaction;
import com.stockvision.repositories.TransactionRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Payout;
import com.stripe.model.checkout.Session;
import com.stripe.param.PayoutCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stockvision.models.Wallet;
import com.stockvision.repositories.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
<<<<<<< HEAD
=======
@CrossOrigin(origins = "http://localhost:5173/")
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

<<<<<<< HEAD
=======
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<?> getWallet(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(403).body("Unauthorized");
        }

        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            return ResponseEntity.status(404).body("Wallet not found");
        }

        // Fetch transactions for this user
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        wallet.setTransactions(transactions); // Attach transactions to wallet response

        return ResponseEntity.ok(wallet);
    }

>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
    @PostMapping("/deposit")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Map<String, Object> request, HttpServletRequest httpRequest) {
        try {
            String userId = (String) httpRequest.getAttribute("userId");
<<<<<<< HEAD
            long amount = ((Number) request.get("amount")).longValue() * 100; // Convert to cents
=======
            double amount = Double.parseDouble(request.get("amount").toString());
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
<<<<<<< HEAD
                    .setClientReferenceId(userId)  // Store userId for later use
=======
                    .setClientReferenceId(userId)
                    .putMetadata("userId", userId)
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("usd")
<<<<<<< HEAD
                                    .setUnitAmount(amount)
=======
                                    .setUnitAmount((long) (amount * 100)) // Convert USD to cents
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Wallet Deposit")
                                            .build())
                                    .build())
                            .build())
<<<<<<< HEAD
                    .build();

            Session session = Session.create(params);

            return ResponseEntity.ok(Map.of("sessionId", session.getId(), "message", "Payment session created"));

        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Payment initiation failed", "message", e.getMessage()));
        }
    }
=======
                    .setSuccessUrl("http://localhost:5173")  // Change later when frontend is ready
                    .setCancelUrl("http://localhost:5173")   // Change later when frontend is ready
                    .build();

            Session session = Session.create(params);
            return ResponseEntity.ok(Map.of("sessionId", session.getId()));

        } catch (StripeException | NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Payment initiation failed", "message", e.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawFunds(@RequestBody Map<String, Object> request, HttpServletRequest httpRequest) {
        try {
            String userId = (String) httpRequest.getAttribute("userId");
            String amountStr = request.get("amount").toString();
            double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid withdrawal amount"));
            }

            Wallet wallet = walletRepository.findByUserId(userId);
            if (wallet == null || wallet.getBalance() < amount) {
                return ResponseEntity.badRequest().body(Map.of("error", "Insufficient balance"));
            }

            wallet.setBalance(wallet.getBalance() - amount);
            walletRepository.save(wallet);

            return ResponseEntity.ok(Map.of("message", "Withdrawal successful", "remainingBalance", wallet.getBalance()));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body(Map.of("error", "Invalid amount format"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }

>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
}
