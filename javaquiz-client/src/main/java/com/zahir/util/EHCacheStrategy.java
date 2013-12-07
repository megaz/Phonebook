package com.zahir.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EHCacheStrategy implements CacheStrategy {

	private CacheManager cacheManager; 
	Cache memoryOnlyCache;
	//default 24 hours
	private int hours = 24 * 60 * 60;
	
	public EHCacheStrategy() {
		cacheManager = CacheManager.getInstance();
		memoryOnlyCache = new Cache("name", 200, false, false, hours, hours);
		cacheManager.addCache(memoryOnlyCache);
	}
	
	public void setTTL(int hours) {
		this.hours = hours;
	}
	
	public void put(Object key, Object value) {
		memoryOnlyCache.put(new Element(key,value));
	}
	public Object get(Object key) {
		Element elem = memoryOnlyCache.get(key);
		if(elem == null){
			return elem;
		}
		return elem.getObjectValue();
	}
}
