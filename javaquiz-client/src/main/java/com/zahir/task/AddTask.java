package com.zahir.task;

import javax.swing.SwingWorker;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.zahir.context.ContactContext;
import com.zahir.util.HTTPMethodFactory;

/**
 * This class uses a SwingWorker for handling threads for HTTP Requests. 
 * 
 * @author zahir
 *
 */
public class AddTask extends SwingWorker<Integer, String> {

	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private ContactContext context;
	public static final Logger LOG = Logger.getLogger(AddTask.class);

	public AddTask(ContactContext context) {
		this.context = context;
	}

	/**
	 * 
	 * Posts a new Contact to the server via HTTP POST 
	 * 
	 * @return Integer The response from the HTTP POST to the server 
	 **/
	@Override
	protected Integer doInBackground() throws HTTPTaskException {

		HttpRequestBase httpPost = new HTTPMethodFactory(context).createPost();
		CloseableHttpResponse response = null;
		if (httpPost != null) {
			try {
				response = httpclient.execute(httpPost);
			} catch (Exception e) {
				LOG.error("Could not send POST reqest to server", e);
				throw new HTTPTaskException(
						"Problem sending POST request to server", e);
			}
		}
		return response.getStatusLine().getStatusCode();
	}
}