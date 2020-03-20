package com.sap.cache.evictionstrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cache.bo.CacheBO;
import com.sap.cache.dto.DelayedCacheObject;
import com.sap.cache.utils.CacheUtils;

@Component

public class TimeBasedEvictionStrategy {

	@Autowired
	private CacheBO cacheBO;

	public TimeBasedEvictionStrategy() {
	}

	public void evict() {
		try {

			DelayedCacheObject delayedCacheObject = CacheUtils.getCleaningupqueue().take();

			System.out.println("cleaning object with key " + delayedCacheObject.getKey());
			cacheBO.getCache().remove(delayedCacheObject.getKey(), delayedCacheObject.getRef());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
