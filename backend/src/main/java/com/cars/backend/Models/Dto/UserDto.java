package com.cars.backend.Models.Dto;

public class UserDto {
	private String email;
	private String firstName;
	private String secondName;

	public UserDto() {
	}
	public UserDto(String email, String firstName, String secondName) {
		this.email = email;
		this.firstName = firstName;
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public UserDto setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public UserDto setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getSecondName() {
		return secondName;
	}

	public UserDto setSecondName(String secondName) {
		this.secondName = secondName;
		return this;
	}
}
