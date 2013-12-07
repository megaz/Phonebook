package com.zahir.context;

/**
 * Holds Contact name and phone number Information so it can be shared across 
 * the application. 
 * 
 * @author zahir
 * 
 **/
public class ContactContext {

	private String name;
	private String phoneNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static class Builder {

		private ContactContext contactContext = new ContactContext();

		public Builder addName(String name) {
			contactContext.setName(name);
			return this;
		}

		public Builder addPhoneNumber(String phoneNumber) {
			contactContext.setPhoneNumber(phoneNumber);
			return this;
		}

		public ContactContext build() {
			return this.contactContext;
		}
	}
}
