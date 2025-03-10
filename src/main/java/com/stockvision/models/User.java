package com.stockvision.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id; // Firebase UID
    private String name;
    private String email;
    private String stripeAccountId; // For withdrawals
<<<<<<< Updated upstream
    private boolean socialLogin;
=======
    private boolean socialLogin;  // Use 'socialLogin' instead of 'isSocialLogin'
>>>>>>> Stashed changes
    private Date createdAt = new Date();
}
