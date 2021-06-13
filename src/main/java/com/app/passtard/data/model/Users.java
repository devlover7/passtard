package com.app.passtard.data.model;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Users {

	@NotNull(message = "first name cannot be null")
	@Size(min=2,message = "first name cannot be less than 2 characters")
	private String firstName;
	
	@NotNull(message = "last name cannot be null")
	@Size(min=2,message = "last name cannot be less than 2 characters")
	private String lastName;
	
	@NotNull(message = "password cannot be null")
	@Size(min=8,max=16,message = "password cannot be less than 8 characters")
	private String password;
	
	@NotNull(message = "email cannot be null")
	@Pattern(regexp = "^[A-Za-z]\\w*$")
	private String userid;
}