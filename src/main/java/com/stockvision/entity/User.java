package com.stockvision.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Document("UserProfile")
@Data
public class User {
	
	@Id
	private String id;
	@Field("FirstName")
	@NotBlank(message =  "First Name required")
	private String firstName;
	@Field("LastName")
	@NotBlank(message = "Last Name required")
	private String lastName;
	@Field("Email")
	@NotBlank(message = "Email is required")
	@Email(message =  "Invalid email format")
	private String email;
	@Field("Contact")
	private String contact;
	
}
