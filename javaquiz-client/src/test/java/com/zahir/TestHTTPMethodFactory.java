package com.zahir;

import java.net.MalformedURLException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zahir.context.ContactContext;
import com.zahir.util.HTTPMethodFactory;

public class TestHTTPMethodFactory {

    private ContactContext context;
    private HTTPMethodFactory methodFactory;
    private static final String EXPECTED_GET_REQUEST_WITH_NAME_NUMBER = "http://localhost:8080/javaquiz-server-0.1.0-SNAPSHOT/phonebook/search?name=Zahir&phoneNumber=123456789";
    private static final String EXPECTED_GET_REQUEST_WITH_NAME = "http://localhost:8080/javaquiz-server-0.1.0-SNAPSHOT/phonebook/search/name/zahir";
    private static final String EXPECTED_GET_REQUEST_NO_NAME_NO_PHONENUMBER = "http://localhost:8080/javaquiz-server-0.1.0-SNAPSHOT/phonebook/search?name=%20&phoneNumber=%20";
    private static final String EXPECTED_ADD_REQUEST = "http://localhost:8080/javaquiz-server-0.1.0-SNAPSHOT/phonebook/add";
    
    @Before
    public void setUp() {
    	context = new ContactContext.Builder().addName("Zahir").addPhoneNumber("123456789").build();
    	methodFactory = new HTTPMethodFactory(context);
    }
    
	@Test
	public void testCreateGetWithNameAndPhoneNumber() {
		HttpRequestBase base = methodFactory.createGet();
		Assert.assertTrue (base instanceof HttpGet);
		try {
			Assert.assertEquals(EXPECTED_GET_REQUEST_WITH_NAME_NUMBER, base.getURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			Assert.fail("HTTPMethodFactory.createGet() seems to be returning a wrong GET url!");
		}
	}
	
	@Test
	public void testCreateGetWithName() {
		
		context.setPhoneNumber(null);
		
		HttpRequestBase base = methodFactory.createGet();
		Assert.assertTrue (base instanceof HttpGet);
		try {
			Assert.assertEquals(EXPECTED_GET_REQUEST_WITH_NAME, base.getURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			Assert.fail("HTTPMethodFactory.createGet() seems to be returning a wrong GET url!");
		}
	}
	
	
	@Test
	public void testCreateGetWithEmptyNameAndEmptyPhoneNumber() {
		
		context.setName(null);
		context.setPhoneNumber(null);
		
		HttpRequestBase base = methodFactory.createGet();
		Assert.assertTrue (base instanceof HttpGet);
		try {
			Assert.assertEquals(EXPECTED_GET_REQUEST_NO_NAME_NO_PHONENUMBER, base.getURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			Assert.fail("HTTPMethodFactory.createGet() seems to be returning a wrong GET url!");
		}
	}
	
	@Test
	public void testCreatePostWithNameAndPhoneNumber() {
		
		HttpRequestBase base = methodFactory.createPost();
		Assert.assertTrue (base instanceof HttpPost);
		try {
			Assert.assertEquals(EXPECTED_ADD_REQUEST, base.getURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			Assert.fail("HTTPMethodFactory.createGet() seems to be returning a wrong GET url!");
		}
	}
	
	@Test
	public void testCreatePostWithEmptyNameAndEmptyPhoneNumber() {
		
		context.setName(null);
		context.setPhoneNumber(null);
		
		 Assert.assertNull(methodFactory.createPost());
		}
}
