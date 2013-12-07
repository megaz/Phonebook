package com.zahir;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zahir.cache.CacheSingleton;
import com.zahir.cache.CacheStrategy;

public class TestEHCacheStrategy {

	public CacheStrategy strategy;
	public static final String TEST_CACHE_KEY = "test";
	public static final String TEST_CACHE_VALUE = "123";
	
	@Before
	public void setup() {
		strategy = CacheSingleton.getInstance();
	}
	
	@Test
	public void testCacheSingletonDoesNotReturnNull() {
		Assert.assertNotNull(strategy);
	}
	
	@Test
	public void testCacheObjectReturnsCachedObject() {
		strategy.put(TEST_CACHE_KEY, TEST_CACHE_VALUE);
		Assert.assertEquals(TEST_CACHE_VALUE, strategy.get(TEST_CACHE_KEY));
	}
	
}
