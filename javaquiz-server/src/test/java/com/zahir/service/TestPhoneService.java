package com.zahir.service;

import java.util.List;

import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.zahir.entity.Contact;
import com.zahir.service.PhonebookServiceImpl;

@RunWith(JukitoRunner.class)
public class TestPhoneService {

	@Inject private PhonebookServiceImpl service;
	private Contact contact;
	
	@Before
	public void setUp() {
		contact = new Contact();
		contact.setName("foobar");
		contact.setPhoneNumber("123456789");
		service.save(contact); 
		service.save(new Contact()); 
	}
	
	@Test 
	public void testPhoneServiceFunctionality() {
		Contact con = new  Contact();
		con.setName("blah");
		con.setPhoneNumber("123");
		Assert.assertTrue(service.save(con)); 
		
		List<Contact> contacts = service.getAllContacts();
		Assert.assertEquals(3, contacts.size());
		Assert.assertEquals("foobar", contacts.get(2).getName());
		Assert.assertEquals("123456789", contacts.get(2).getPhoneNumber());
		
		List<Contact> searchContacts = service.searchPhonebook("foo");
		Assert.assertEquals(1, searchContacts.size());
		Assert.assertEquals("foobar", searchContacts.get(0).getName());
		
		Contact searchcontact = service.searchPhonebook("blah", "123");
		Assert.assertEquals("blah", searchcontact.getName());
		
		
	}
	
}
