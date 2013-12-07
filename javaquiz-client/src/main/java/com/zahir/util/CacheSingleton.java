package com.zahir.util;

public class CacheSingleton {

	private static CacheStrategy cacheStrategy = null;

	private CacheSingleton() { }

	public static synchronized CacheStrategy getInstance() {
		if (cacheStrategy == null) {
			cacheStrategy = new EHCacheStrategy();
		}
		return cacheStrategy;
	}
}