package com.zahir;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zahir.context.ContactContext;
import com.zahir.task.SearchTaskHandler;

public class TestSearchTaskHandler extends TestEHCacheStrategy{

	private SearchTaskHandler handler;
	private ContactContext context;
	private static final String SINGLE_JSON = "[{\"id\":1,\"name\":\"foo\",\"phoneNumber\":\"007007007\"}]";
	private static final String MULTIPLE_JSON = "[{\"id\":2,\"name\":\"bar\",\"phoneNumber\":\"008008008\"},"
			+ "{\"id\":3,\"name\":\"zahir\",\"phoneNumber\":\"123456789\"}]";
	
	@Before
	public void setUp() {
		context = new ContactContext.Builder()
		.addName("zahir")
		.addPhoneNumber("12345").build();
		handler = new SearchTaskHandler(context);
		super.setup();
	}
	 
	@Test
	public void testCacheResultsReturned() throws InterruptedException, ExecutionException {
		this.strategy.put("zahir12345", context);
		Assert.assertEquals("zahir", handler.executeTask().get(0).getName());
	}
	
	@Test
	public void testProcessResultSingleJSON() {
		List<ContactContext> contact = handler.processResult(SINGLE_JSON);
		Assert.assertEquals(1, contact.size());
		Assert.assertEquals("foo", contact.get(0).getName());
		Assert.assertEquals("007007007", contact.get(0).getPhoneNumber());
		
	}
	
	@Test
	public void testProcessResultMultipleJSON() {

		List<ContactContext> contacts = handler.processResult(MULTIPLE_JSON);
		Assert.assertEquals(2, contacts.size());
		Assert.assertEquals("bar", contacts.get(0).getName());
		Assert.assertEquals("008008008", contacts.get(0).getPhoneNumber());
		Assert.assertEquals("zahir", contacts.get(1).getName());
		Assert.assertEquals("123456789", contacts.get(1).getPhoneNumber());
	}
	
	@Test
	public void testProcessEmptyJSON() {
		List<ContactContext> contacts = handler.processResult("");
		Assert.assertTrue(contacts.isEmpty());
	}
}
