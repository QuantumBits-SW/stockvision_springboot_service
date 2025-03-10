package com.stockvision.controllers;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stockvision.models.Wallet;
import com.stockvision.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Autowired
    private WalletRepository walletRepository;

    @PostMapping("/webhook")
    public ResponseEntity<?> handleStripeWebhook(HttpServletRequest request) {
        try {
            String payload = new BufferedReader(request.getReader()).lines().collect(Collectors.joining());
            Event event = Event.GSON.fromJson(payload, Event.class);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                String userId = session.getClientReferenceId();
                double amount = session.getAmountTotal() / 100.0; // Convert to dollars

                Wallet wallet = walletRepository.findByUserId(userId);
                if (wallet == null) {
                    wallet = new Wallet();
                    wallet.setUserId(userId);
                    wallet.setBalance(amount);
                } else {
                    wallet.setBalance(wallet.getBalance() + amount);
                }
                walletRepository.save(wallet);

                return ResponseEntity.ok(Map.of("status", "success", "message", "Payment successful", "amount", amount));
            }

            return ResponseEntity.ok(Map.of("status", "ignored", "message", "Event not processed"));

        } catch (IOException e) {
            return ResponseEntity.status(400).body(Map.of("error", "Webhook error", "message", e.getMessage()));
        }
    }
}
