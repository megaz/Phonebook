package com.zahir.util;

public interface CacheStrategy {

	 void setTTL(int hours);
	
	 void put(Object key, Object value);
	
	 Object get(Object key);
	
}
