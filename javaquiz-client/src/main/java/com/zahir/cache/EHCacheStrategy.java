package com.zahir.cache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Responsible for providing a Caching mechanism based on the implemented  
 * CacheStrategy interface.  EHCache strategy is used for Caching
 * 
 * @author zahir
 * 
 **/
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
	
	/**
	 * (non-Javadoc)
	 * @see com.zahir.cache.CacheStrategy#setTTL(int)
	 * 
	 * Sets the TTL for a Cache which defines when the cache exipres
	 * 
	 * @param hours  Number of hours the cache is active  
	 **/
	public void setTTL(int hours) {
		this.hours = hours;
	}
	
	/**
	 *  Puts a object in the cache.  If the key already exists, replaces 
	 *  it with the new object.
	 *  
	 *   @param key The key to identify the object cached
	 *   @param value The object being cached 
	 */
	public void put(Object key, Object value) {
		memoryOnlyCache.put(new Element(key,value));
	}
	
	/**
	 *   Gets an object from the cache.  
	 *  
	 *   @param key The key to identify the object (value) being returned 
	 *   @return value The object in the cache
	 *  
	 */
	public Object get(Object key) {
		Element elem = memoryOnlyCache.get(key);
		if(elem == null){
			return elem;
		}
		return elem.getObjectValue();
	}
}
