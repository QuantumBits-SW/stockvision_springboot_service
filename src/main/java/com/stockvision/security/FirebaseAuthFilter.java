package com.stockvision.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.stockvision.models.User;
import com.stockvision.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {

<<<<<<< HEAD
    @Autowired
    private UserRepository userRepository;
=======
    public final UserRepository userRepository;

    public FirebaseAuthFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

<<<<<<< HEAD
=======
        if (request.getRequestURI().equals("/stockvision/api/stripe/webhook")) {
            filterChain.doFilter(request, response);
            return;
        }

>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String userId = decodedToken.getUid();
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();

            // Check if user exists, if not, create one
            User existingUser = userRepository.findById(userId).orElse(null);
            if (existingUser == null) {
                User newUser = new User();
                newUser.setId(userId);
                newUser.setEmail(email);
                newUser.setName(name);
<<<<<<< HEAD
                newUser.setIsSocialLogin(true);
=======
                newUser.setSocialLogin(true);
>>>>>>> eedf6ad52a2e6940ad7bbd1ad8c9a19e228b57bc
                userRepository.save(newUser);
            }

            request.setAttribute("userId", userId);
            filterChain.doFilter(request, response);

        } catch (FirebaseAuthException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Firebase token");
        }
    }


}
