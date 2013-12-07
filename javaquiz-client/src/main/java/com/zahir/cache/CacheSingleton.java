package com.zahir.cache;


/**
 * Provides a Cache CacheStrategy so the application can all access one instance 
 * of the shared cache
 * 
 * @author zahir
 * 
 **/
public class CacheSingleton {

	private static CacheStrategy cacheStrategy = null;

	private CacheSingleton() { }

	/**
	 * Creates a CacheStrategy if it hasn't been created.
	 * 
	 * @return CacheStrategy
	 * 
	 **/
	public static synchronized CacheStrategy getInstance() {
		if (cacheStrategy == null) {
			cacheStrategy = new EHCacheStrategy();
		}
		return cacheStrategy;
	}
}