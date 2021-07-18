package com.banking.beans;

import javax.validation.constraints.Size;

public class Customer {

	private int Id;
	@Size(min=3, message="Firstname must be greater than 3")
	private String firstName;

	@Size(min=3, message="Last must be greater than 3")
	private String lastName;

	@Size(min=5, message="Email must be greater than 5")
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

}
