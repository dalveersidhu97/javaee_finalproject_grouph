package com.banking.beans;

import javax.validation.constraints.Size;

public class Register {

	@Size(min=4, message="username must be greater than 4. ")
	private String username;
	@Size(min=8, message="username must be greater than 8. ")
	private String password;
	private int Id;
	@Size(min=3, message="username must be greater than 3. ")
	private String firstName;
	@Size(min=3, message="username must be greater than 3. ")
	private String lastName;
	@Size(min=5, message="username must be greater than 5. ")
	private String email;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
