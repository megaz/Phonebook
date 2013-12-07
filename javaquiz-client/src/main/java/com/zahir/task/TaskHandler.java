package com.zahir.task;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Provides a Task interface for common tasks. 
 * 
 * @author zahir
 * 
 * @param T The type of object you want to return whilst executing tasks.
 * 
 **/
public interface TaskHandler<T> {

	List<T> executeTask() throws InterruptedException, ExecutionException;
	
	List<T> processResult(String json);
}
