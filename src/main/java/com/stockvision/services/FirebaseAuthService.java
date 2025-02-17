package com.stockvision.services;

import java.io.FileInputStream;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Service
public class FirebaseAuthService {

	private static FirebaseAuth firebaseAuth;
	public void initializeFirebaseAuth()
	{
		try {
		
			FileInputStream serviceAccount = new FileInputStream("path/to/your/serviceAccountKey.json");
	
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .build();
	
			FirebaseApp.initializeApp(options);
			
		} 
		catch (Exception e) {
			System.out.println("Error in initializing firebase App :" + e.getMessage());
			return ;
		}
		System.out.println("Firebase App  initialized successfully!!");
		firebaseAuth = FirebaseAuth.getInstance();
	}
	
	public boolean verifyToken(String token)
	{
		try {
			if(firebaseAuth == null)
			{
				initializeFirebaseAuth();
			}
			FirebaseToken decodeToken = firebaseAuth.verifyIdToken(token);
			//further actions needs to be done.
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
