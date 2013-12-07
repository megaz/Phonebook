package com.zahir.task;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.zahir.context.ContactContext;

/**
 * This class handles Adding a Contact to a Phonebook by sending HTTP POST Requests 
 * using a SwingWorker for handling threads. 
 * 
 * @author zahir
 *
 */
public class AddTaskHandler {

	private AddTask addTask;
	public static final Logger LOG = Logger.getLogger(AddTaskHandler.class);
	
	public AddTaskHandler(ContactContext contact) {
		this.addTask = new AddTask(contact);
	}

	/**
	 * (non-Javadoc)
	 * @see com.zahir.task.TaskHandler#executeTask()
	 * 
	 * Posts a new Contact to the server and handles the execution of the task.
	 * Returns HTTP 201 if created, else it was not created on the server.  
	 * 
	 * @return Integer The response from the HTTP POST to the server 
	 **/
	public Integer executeTask() throws InterruptedException, ExecutionException {
		try {
			addTask.execute();
		} catch (Exception e) {
			LOG.error("No Contact was added in the POST request", e);
			return 0;
		}
		return addTask.get();
	}
}
