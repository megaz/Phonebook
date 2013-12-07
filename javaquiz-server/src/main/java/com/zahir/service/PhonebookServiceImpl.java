package com.zahir.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zahir.dao.core.ContactDAO;
import com.zahir.entity.Contact;

import core.zahir.exceptions.EntitiyException;

/**
 * Phone Service that handles calls to the DAO layer
 * Future work - Caching
 * @author Zahir
 **/
@Singleton 
public class PhonebookServiceImpl implements PhonebookService {

	@Inject private ContactDAO contactDao;
	
	@Override
	public boolean save(Contact contact) {
	try {	
		contactDao.persist(contact);
	}
	catch(EntitiyException e) {
		//log error
		throw e;
	}
		return true;
	}

	@Override
	public List<Contact> getAllContacts() {
		List<Contact> contacts = contactDao.findAll();
		return contacts;
	}

	@Override
	public List<Contact> searchPhonebook(String name) {
		return contactDao.searchPhonebook(name);
	}
 
	@Override
	public Contact searchPhonebook(String name, String phoneNumber) {
		try {
		return contactDao.searchPhonebook(name.toLowerCase(), phoneNumber);
		}
		catch(Exception e){
			return null;
		}
	}

}
