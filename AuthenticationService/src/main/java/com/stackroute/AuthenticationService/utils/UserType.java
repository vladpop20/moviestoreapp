package com.stackroute.AuthenticationService.utils;

public enum UserType {
	ADMIN("admin"), CUSTOMER("customer");

	UserType(String type) {
		this.type = type;
	}
	private String type;

	public String getType() {
		return type;
	}
}
