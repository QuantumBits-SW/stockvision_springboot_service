package com.stockvision.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String contact;
	@JsonProperty
	private boolean isNewUser;
	
	
}