package com.zahir.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zahir.context.ContactContext;

/**
 * Responsible for building HTTP GET and POST Requests based on ContactContext
 * Defines REST Endpoints that can be moved in a separate class later. 
 * 
 * @author zahir
 * 
 **/
public class HTTPMethodFactory {
	
	private ContactContext context;
	
	private static final String SERVER_BASE_ENDPOINT = "http://localhost:8080/javaquiz-server-0.1.0-SNAPSHOT/phonebook/";  
	private static final String SEARCH_NAME_ENDPOINT = SERVER_BASE_ENDPOINT + "search/name/%s";
	private static final String SEARCH_NAME_NUMBER_ENDPOINT = SERVER_BASE_ENDPOINT + "search?name=%s&phoneNumber=%s";
	private static final String ADD_CONTACT_ENDPOINT = SERVER_BASE_ENDPOINT + "add";
	private static final String APPLICATION_JSON = "application/json";
	
	
	public static final Logger LOG = Logger.getLogger(HTTPMethodFactory.class);
	
	public HTTPMethodFactory(ContactContext context) {
		this.context = context;
	}
	
	/**
	 *  Creates a base for HTTP Requests based on inputs from ContactContext
	 *  Typically a GET request 
	 *  @return HttpRequestBase 
	 * 
	 **/
	public HttpRequestBase createGet() {
		String url;
		
		if(isNameAndPhoneNumberPresent()){
			url = replaceBlankWithPercentageTwenty(String.format(SEARCH_NAME_NUMBER_ENDPOINT, context.getName(),context.getPhoneNumber()));
		}
		else if(isNamePresent()) {
			url = replaceBlankWithPercentageTwenty(String.format(SEARCH_NAME_ENDPOINT, context.getName().toLowerCase()));
		}
		else {
			url = String.format(SEARCH_NAME_NUMBER_ENDPOINT, "%20","%20");
		}
		LOG.info("Sending GET Request " + url);
		return new HttpGet(url);
	}
	
	/**
	 *  Creates a base for HTTP Requests based on inputs from ContactContext
	 *  Typically a POST request 
	 *  @return HttpRequestBase 
	 * 
	 **/
	public HttpRequestBase createPost() {
		if(isNameAndPhoneNumberPresent()) {
			String json = new Gson().toJson(context);
			LOG.info(String.format("Sending POST Request to url [%s] with the following json [%s]", ADD_CONTACT_ENDPOINT, json));
			HttpPost p = new HttpPost(ADD_CONTACT_ENDPOINT);
			p.setEntity(new StringEntity(json, ContentType.create(APPLICATION_JSON)));
			return p;
		}
		return null;
	}
	
	
	private boolean isNameAndPhoneNumberPresent() {
		return StringUtils.isNotBlank(context.getName()) && StringUtils.isNotBlank(context.getPhoneNumber());
	}
	
	private boolean isNamePresent() {
		return StringUtils.isNotBlank(context.getName());
	}
	
	private String replaceBlankWithPercentageTwenty(String url) {
		return url.replaceAll(" ", "%20");
	}
}
