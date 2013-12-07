package com.zahir.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zahir.cache.CacheSingleton;
import com.zahir.cache.CacheStrategy;
import com.zahir.context.ContactContext;

/**
 * This class handles Searching a Phonebook on the server by sending HTTP GET Requests 
 * using a SwingWorker for handling threads.  It also uses EHCache for caching results 
 * we have already obtained to save http requests.
 * 
 * @author zahir
 *
 */
public class SearchTaskHandler implements TaskHandler<ContactContext>{

	private static final int JSON_MINIMUM_LENGTH = 10;
	private SearchTask task;
	private CacheStrategy cacheStrategy;
	public static final Logger LOG = Logger.getLogger(SearchTaskHandler.class);
	
	private ContactContext contact;
	
	public SearchTaskHandler(ContactContext contact) {
		this.task = new SearchTask(contact);
		this.cacheStrategy = CacheSingleton.getInstance();
		this.contact = contact;
	}

	/**
	 * (non-Javadoc)
	 * @see com.zahir.task.TaskHandler#executeTask()
	 * 
	 * Checks whether contact is in cache, if not then executes a HTTP Request to retrieve contact
	 * from the server
	 * 
	 * @return List<ContactContext> A list of ContactContext that match the search
	 **/
	public List<ContactContext> executeTask() throws InterruptedException, ExecutionException {
		
		Object value =  cacheStrategy.get(contact.getName() + contact.getPhoneNumber());
		if(value != null) {
			LOG.info("Retrieved contact from cache");
			ContactContext contact = (ContactContext) value;
			List<ContactContext> list = new ArrayList<ContactContext>();
			list.add(contact);
			return list;
		}
		else {
			task.execute();
			return processResult(task.get());
		}
		
	}

	/*
	 * Binds the JSON returned into a List of ContactContext
	 */
	private List<ContactContext> resultJsonList(String json, Gson gson) {
		ContactContext[] contentArray = gson.fromJson(json,
				ContactContext[].class);
		return Arrays.asList(contentArray);

	}

	/**
	 * Processes JSON returned from the server and some conditions to determine if we have
	 * retrieved a single contact or multiple contacts from the server
	 *  
	 *  @return List<ContactContext> A list of ContactContext that match the search criteria
	 */
	public List<ContactContext> processResult(String json) {
		Gson gson = new Gson();

		if (StringUtils.isNotBlank(json) && json.length() > JSON_MINIMUM_LENGTH) {

			if (json.startsWith("[")) {
				return resultJsonList(json, gson);
			}

		}
		return new ArrayList<ContactContext>();
	}
}