package com.zahir.task;

import javax.swing.SwingWorker;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.zahir.context.ContactContext;
import com.zahir.util.HTTPMethodFactory;

/**
 * This class uses a Swing Worker to execute searches to the server using HTTP GET requests  
 * 
 * @author zahir
 *
 */
public class SearchTask extends SwingWorker<String, String> {

	private CloseableHttpClient httpclient;  
	
	private ContactContext context;
	
	public static final Logger LOG = Logger.getLogger(SearchTask.class);
	
	public SearchTask(ContactContext context) {
		this.context = context;
		this.httpclient = HttpClients.createDefault();
	}
	
	  @Override
	  protected String doInBackground()  {
		
		  HttpRequestBase httpGet = new HTTPMethodFactory(context).createGet();
		  ResponseHandler<String> responseHandler = new BasicResponseHandler();
		  String response = null;
		  try {
			  response = httpclient.execute(httpGet, responseHandler);
		  }
		  catch(Exception e) {
			  LOG.error("Error sending GET request ", e);
		  }
	    return response;
	  }
	}