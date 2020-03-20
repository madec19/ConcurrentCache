package com.sap.cache.evictionstrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sap.cache.bo.CacheBO;
import com.sap.cache.dto.DelayedCacheObject;
import com.sap.cache.utils.CacheUtils;

@Component
public class SizeBasedEvictionStrategy implements EvictionStrategy {

	@Value(value = "${maxCap}")
	private String maxCapacity;

	@Autowired
	private CacheBO cacheBO;

	public SizeBasedEvictionStrategy() {
	}

	
	@Override
	public void evict() {

		if (cacheBO.getCache().size() > Integer.parseInt(maxCapacity)) {
			DelayedCacheObject delayedCacheObject = CacheUtils.getCleaningupqueue().peek();

			CacheUtils.getCleaningupqueue().remove(delayedCacheObject);
			System.out.println("size based eviction, removing object with key " + delayedCacheObject.getKey());
			cacheBO.getCache().remove(delayedCacheObject.getKey(), delayedCacheObject.getRef());
		}

	}

}
