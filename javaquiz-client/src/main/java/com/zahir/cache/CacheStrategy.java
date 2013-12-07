package com.zahir.cache;

/**
 * Provides a Cache interface so implementations can be plugged in
 * 
 * @author zahir
 * 
 **/
public interface CacheStrategy {

	 void setTTL(int hours);
	
	 void put(Object key, Object value);
	
	 Object get(Object key);
	
}
