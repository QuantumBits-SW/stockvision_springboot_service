package com.stockvision.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseToken;
import com.stockvision.entity.User;
import com.stockvision.entity.UserRequest;
import com.stockvision.repository.UserRepository;
import com.stockvision.services.FirebaseAuthService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AuthController {

	@Autowired
	FirebaseAuthService firebaseAuthService;
	
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/getUserProfile")
	public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authToken,
			@RequestBody UserRequest userRequest)
	{
		if(authToken == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).
					body("Authorization header missing");
		
		try 
		{
			FirebaseToken firebaseDecodedToken = firebaseAuthService.verifyToken(authToken);
			if(firebaseDecodedToken == null)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
			
			String email = firebaseDecodedToken.getEmail();
			
			if(userRequest.isNewUser())
			{
				Optional<User> existingUser = userRepository.findByEmail(email);			 
				 if(existingUser.isEmpty())
				 {
					 User user = new User();
					 user.setFirstName(userRequest.getFirstName());
					 user.setLastName(userRequest.getLastName());
					 user.setEmail(email);
					 user.setContact(userRequest.getContact());
					 
					 User mongoUser = userRepository.save(user);
					 
					return ResponseEntity.ok(mongoUser);

				 }	
				 else {
					 return ResponseEntity.ok(existingUser.get());
				 }
			}
			else 
			{
				Optional<User> existingUser = userRepository.findByEmail(email);
				 if(existingUser.isEmpty())
					 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
				 
				return ResponseEntity.ok(existingUser.get());
			}
			
		}
		catch (Exception e) {
			System.out.println("Error in getUserProfile : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
