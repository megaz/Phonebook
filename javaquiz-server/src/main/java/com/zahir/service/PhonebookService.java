package com.zahir.service;

import java.util.List;

import com.zahir.entity.Contact;


public interface PhonebookService {

	boolean save(Contact contact);
	
	List<Contact> getAllContacts();
	
	List<Contact> searchPhonebook(String name);
	
	Contact searchPhonebook(String name, String number);
	
}
